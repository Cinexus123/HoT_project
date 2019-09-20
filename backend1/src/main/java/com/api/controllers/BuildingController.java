package com.api.controllers;

import com.api.config.properties.LogMessages;
import com.api.models.Building;
import com.api.dto.BuildingDto;
import com.api.models.Floor;
import com.api.commands.FloorCommand;
import com.api.services.BuildingStorageService;
import com.api.services.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AllArgsConstructor
@Validated
@RequestMapping("/buildings")
public class BuildingController {

    private final BuildingStorageService buildingStorageService;

    @PostMapping("")
    public ResponseEntity<BuildingDto> uploadBuilding(@RequestParam(value = "files",required = false) @Valid List<FloorCommand> files,
                                      @RequestParam("buildingName") @Pattern(regexp = "^\\w+$") String buildingName) {
        log.info(LogMessages.buildingsUpload, buildingName,
                files.stream().map(FloorCommand::getFile).collect(Collectors.toList()));

        Building building = buildingStorageService.create(buildingName, files);
        return ResponseEntity.ok(
                new BuildingDto(buildingName, building.getFloorNames().stream()
                .map(floor -> new Floor(floor, buildLinkToImage(building.getName(), floor)))
                .collect(Collectors.toList())));
    }

    @PostMapping("/{buildingName}")
    public ResponseEntity<Floor> uploadBuildingFloor(@Valid @RequestParam("file") FloorCommand floor,
                                                     @PathVariable String buildingName){
        log.info(LogMessages.buildingsUploadFloor,floor.getFile().getOriginalFilename(),buildingName);

        buildingStorageService.createFloor(buildingName,floor);
        return ResponseEntity.ok(
                new Floor(floor.getFile().getOriginalFilename(),buildLinkToImage(buildingName,floor.getFile().getOriginalFilename())));
    }

    @GetMapping("/{buildingName}/{floorName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String buildingName,
                                                 @PathVariable String floorName, HttpServletRequest request) {
        log.info(LogMessages.buildingsDownloadFloor,floorName,buildingName);

        Resource resource = buildingStorageService.getFloorPhoto(buildingName, floorName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        if (contentType == null)
            contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/{buildingName}")
    public ResponseEntity<BuildingDto> getBuilding(@PathVariable String buildingName) {
        log.info(LogMessages.buildingsGetBuilding,buildingName);
        Building building = buildingStorageService.get(buildingName);
        return ResponseEntity.ok(
                new BuildingDto(buildingName,
                        building.getFloorNames().stream()
                        .map(floor -> new Floor(floor, buildLinkToImage(building.getName(), floor)))
                        .collect(Collectors.toList())));
    }

    @GetMapping("")
    public ResponseEntity<List<BuildingDto>> getAllBuildings() {
        log.info(LogMessages.buildingsGetAll);
        List<BuildingDto> list = buildingStorageService.getAll().stream()
                        .map(building -> new BuildingDto(
                                building.getName(),
                                building.getFloorNames().stream().map(
                                        floorName -> new Floor(floorName, buildLinkToImage(building.getName(), floorName)))
                                        .collect(Collectors.toList()))
                        ).collect(Collectors.toList());
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @PatchMapping("/{buildingName}")
    public void renameBuilding(@PathVariable String buildingName, @Valid @RequestBody BuildingDto building){
        log.info(LogMessages.buildingsRenameBuilding,buildingName,building.getName());
        buildingStorageService.rename(buildingName,building.getName());
    }

    @PatchMapping("/{buildingName}/{floorName}")
    public void renameFloor(@PathVariable String buildingName,@PathVariable String floorName,@Valid @RequestBody Floor floor){
        log.info(LogMessages.buildingsRenameFloor,floorName,floor.getName(),buildingName);
        buildingStorageService.renameFloor(buildingName,floorName,floor.getName());
    }

    @DeleteMapping("/{buildingName}")
    public void deleteBuilding(@PathVariable String buildingName){
        log.info(LogMessages.buildingsDeleteBuilding,buildingName);
        buildingStorageService.demolish(buildingName);
    }

    @DeleteMapping("/{buildingName}/{floorName}")
    public void deleteFloor(@PathVariable String buildingName,@PathVariable String floorName){
        log.info(LogMessages.buildingsDeleteFloor,floorName,buildingName);
        buildingStorageService.demolishFloor(buildingName,floorName);
    }

    private String buildLinkToImage(String buildingName, String floor) {
        return ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/buildings/")
                .path("/"+buildingName + "/")
                .path(floor)
                .toUriString();
    }
}


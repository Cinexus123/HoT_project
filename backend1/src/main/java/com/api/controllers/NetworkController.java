package com.api.controllers;

import com.api.commands.NetworkCommand;
import com.api.config.properties.LogMessages;
import com.api.dto.NetworkDto;
import com.api.entities.Network;
import com.api.services.BuildingStorageService;
import com.api.services.MapDrawService;
import com.api.services.NetworkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/networks")
public class NetworkController {

    private final NetworkService networkService;
    private final BuildingStorageService buildingService;
    private final MapDrawService mapDrawService;

    private NetworkDto networkToNetworkDto(Network network) {

        NetworkDto networkDto = new NetworkDto();

        networkDto.setId(network.getId());
        networkDto.setBuilding(network.getBuilding());
        networkDto.setFloor(network.getFloor());
        networkDto.setDescription(network.getDescription());
        networkDto.setName(network.getName());
        networkDto.setXPos(network.getXPos());
        networkDto.setYPos(network.getYPos());


        return networkDto;
    }

    @GetMapping("")
    public List<NetworkDto> getNetworkDtoList() {

        List<NetworkDto> networkDtoList = new ArrayList<>();

        List<Network> networkList = networkService.getAll();

        networkList.forEach(m -> {
            networkDtoList.add(networkToNetworkDto(m));
        });

        log.info(LogMessages.networkGetAll);

        return networkDtoList;
    }

    @GetMapping("/{id}")
    public NetworkDto getNetworkDto(@PathVariable("id") Long id) {

        Network network = networkService.get(id);

        log.info(LogMessages.networkGet, id);

        return networkToNetworkDto(network);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NetworkDto> editSwitch(@RequestBody NetworkCommand networkCommand, @PathVariable Long id) {

        Network network = networkService.get(id);

        network.setBuilding(networkCommand.getBuilding());
        network.setFloor(networkCommand.getFloor());
        network.setDescription(networkCommand.getDescription());
        network.setName(networkCommand.getName());
        network.setXPos(networkCommand.getX());
        network.setYPos(networkCommand.getY());

        networkService.edit(network);

        log.info(LogMessages.networkEdit, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{nickName}/{id}/{timeStamp}/image.png", produces = "image/png")
    public ResponseEntity<byte[]> drawMapWithDescriptionOfSwitch(@PathVariable("nickName") String nickname, @PathVariable("id") Long networkId,
                                                                 @PathVariable("timeStamp") String timeStamp) {
        Network network = networkService.get(networkId);
        Resource resource = buildingService.getFloorPhoto(network.getBuilding(), network.getFloor());
        byte[] img = null;
        try {
            img = mapDrawService.drawPointOnMapWithDescription(nickname, network.getBuilding(), network.getFloor(), network.getDescription()
                    ,resource, network.getXPos(), network.getYPos(),timeStamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Map with location sent for switch: " + networkId); //TODO przenieść log do miejsca logowania

        return new ResponseEntity<>(img, HttpStatus.OK);
    }

    @GetMapping(value = "/{nickName}/{id}/{timeStamp}/map/image.png", produces = "image/png")
    public ResponseEntity<byte[]> drawMapOfSwitch(@PathVariable("nickName") String nickname, @PathVariable("id") Long networkId,
                                                  @PathVariable("timeStamp") String timeStamp) {

        Network network = networkService.get(networkId);
        Resource resource = buildingService.getFloorPhoto(network.getBuilding(), network.getFloor());
        byte[] img = null;
        try {
            img = mapDrawService.drawPointOnMap(resource, nickname, network.getXPos(), network.getYPos());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Map with location sent for switch: " + networkId); //TODO przenieść log do miejsca logowania

        return new ResponseEntity<>(img, HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<Network> addNewNetwork(@RequestBody NetworkCommand networkCommand) {
        Network network = new Network(networkCommand.getName(), networkCommand.getDescription(), networkCommand.getX(), networkCommand.getY(), networkCommand.getBuilding(), networkCommand.getFloor());
        Network savedNetwork = networkService.save(network);
        return new ResponseEntity<>(savedNetwork, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NetworkCommand> deleteNetwork(@PathVariable Long id ) {
        networkService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


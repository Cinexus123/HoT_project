package com.api.services.impl;

import com.api.commands.FloorCommand;
import com.api.exceptions.*;
import com.api.models.Building;
import com.api.services.BuildingStorageService;
import com.api.services.FileStorageService;
import com.api.services.NetworkService;
import com.api.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuildingStorageServiceImpl implements BuildingStorageService {

    private final FileStorageService storageService;
    private final NetworkService networkService;
    private final RoomService roomService;

    @Override
    public boolean exists(String buildingName) {
        return storageService.fileExists(buildingName);
    }

    @Override
    public Building get(String buildingName) {
        try {
            List<String> fileNames = storageService.getFilesNames(buildingName);
            fileNames.removeIf(fileName -> !fileName.contains("."));
            return new Building(buildingName, fileNames);
        } catch (IOException e) {
            throw new BuildingNotFoundException(buildingName);
        }
    }

    @Override
    public List<Building> getAll() {
        try {
            List<Building> buildings = new ArrayList<>();
            List<String> fileNames = storageService.getFilesNames("");
            for (String file : fileNames) {
                List<String> files = storageService.getFilesNames(file);
                buildings.add(new Building(file, files));
            }
            return buildings;
        } catch (IOException e) {
            throw new BuildingException("Buildings", "Fetching all buildings failed.", e);
        }
    }

    @Override
    public List<Resource> getFloorPhotosAsResources(String buildingName) {
        try {
            return storageService.getFilesNames(buildingName).stream()
                    .map(fileName -> getFloorPhoto(buildingName, fileName))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new BuildingException(buildingName, "Fetching floor as resource failed.", e);
        }
    }

    @Override
    public Resource getFloorPhoto(String buildingName, String floorName) {
        try {
            return storageService.loadFileAsResource(buildingName + "/" + floorName);
        } catch (FileNotFoundException | MalformedURLException e) {
            throw new BuildingNotFoundException(buildingName, floorName);
        }
    }

    @Override
    public Building create(String buildingName, List<FloorCommand> floors) {
        buildingName = buildingName.replaceAll(" ", "");
        if (storageService.fileExists(buildingName))
            throw new BuildingAlreadyExistsException(buildingName);

        Building building = new Building(buildingName, new ArrayList<>());

        try {
            storageService.createDir(buildingName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BuildingException(buildingName, "Cannot create directory", e);
        }

        String finalBuildingName = buildingName;
        floors
                .forEach(floor -> {
                    String fileName = prepareFileName(floor.getFile().getOriginalFilename());
                    building.getFloorNames().add(fileName);
                    storageService.storeFile(finalBuildingName + "/" + fileName, floor.getFile());
                });
        return building;
    }

    @Override
    public void createFloor(String buildingName, FloorCommand floor) {
        buildingName = prepareFileName(buildingName);
        if (!storageService.fileExists(buildingName))
            throw new BuildingNotFoundException(buildingName);

        String fileName = prepareFileName(floor.getFile().getOriginalFilename());
        String filePath = buildingName + "/" + fileName;

        if (storageService.fileExists(filePath))
            throw new FloorAlreadyExistsException(buildingName, fileName);

        storageService.storeFile(filePath, floor.getFile());
    }

    @Override
    public void rename(String buildingName, String newBuildingName) {
        if (!storageService.fileExists(buildingName))
            throw new BuildingNotFoundException(buildingName);
        try {
            storageService.renameFile(buildingName, newBuildingName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BuildingException(buildingName, "Renaming building failed: " + e.getMessage(), e);
        }
        roomService.getAllByBuilding(buildingName).forEach(room -> {
            room.setBuilding(newBuildingName);
            roomService.saveRooms(room);
        });
        networkService.getAllByBuilding(buildingName).forEach(network -> {
            network.setBuilding(newBuildingName);
            networkService.save(network);
        });
    }

    @Override
    public void renameFloor(String buildingName, String floorName, String newFloorName) {
        String floorPath = buildingName + "/" + floorName;
        String newFloorPath = buildingName + "/" + newFloorName;

        if (!storageService.fileExists(floorPath))
            throw new FloorNotFoundException(buildingName, floorName);
        if (storageService.fileExists(newFloorPath))
            throw new FloorAlreadyExistsException(buildingName, newFloorName);

        try {
            storageService.renameFile(floorPath, newFloorPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BuildingException(buildingName, floorName, "Renaming floor failed.", e);
        }
        roomService.getAllByBuildingAndFloor(buildingName, floorName).forEach(room -> {
            room.setFloor(newFloorName);
            roomService.saveRooms(room);
        });
        networkService.getAllByBuildingAndFloor(buildingName, floorName).forEach(network -> {
            network.setFloor(newFloorName);
            networkService.save(network);
        });
    }

    @Override
    public void demolish(String buildingName) {
        try {
            storageService.deleteDirectory(buildingName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BuildingException(buildingName, "Deleting building failed.", e);
        }
    }

    @Override
    public void demolishFloor(String buildingName, String floorName) {
        try {
            storageService.deleteFile(buildingName + "/" + floorName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BuildingException(buildingName, floorName, "Deleting floor failed.", e);
        }
    }

    private String prepareFileName(String fileName) {
        if (fileName == null || fileName.trim().equals(""))
            throw new IllegalArgumentException("File name cannot be empty");
        return fileName.replaceAll(" ", "");
    }
}

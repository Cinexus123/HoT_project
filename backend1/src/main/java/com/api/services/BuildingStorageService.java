package com.api.services;

import com.api.commands.FloorCommand;
import com.api.models.Building;
import org.springframework.core.io.Resource;

import java.util.List;

public interface BuildingStorageService {
    boolean exists(String buildingName);

    Building get(String buildingName);

    List<Building> getAll();

    List<Resource> getFloorPhotosAsResources(String buildingName);

    Resource getFloorPhoto(String buildingName, String floorName);

    Building create(String buildingName, List<FloorCommand> files);

    void createFloor(String buildingName, FloorCommand file);

    void rename(String building, String newBuildingName);

    void renameFloor(String building, String floorName, String newFloorName);

    void demolish(String buildingName);

    void demolishFloor(String buildingName, String floorName);
}

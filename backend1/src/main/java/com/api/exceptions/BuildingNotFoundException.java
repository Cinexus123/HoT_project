package com.api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Not found")
public class BuildingNotFoundException extends RuntimeException {

    public BuildingNotFoundException(String buildingName) {
        super("Building with name: "+buildingName+" not found.");
        log.error("Building with name: "+buildingName+" not found.");
    }

    public BuildingNotFoundException(String buildingName, String floorName) {
        super("Floor named: "+floorName+" in building: "+buildingName+" not found.");
        log.error("Floor named: "+floorName+" in building: "+buildingName+" not found.");
    }

}

package com.api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Floor with that name not found")
public class FloorNotFoundException extends  RuntimeException{

    public FloorNotFoundException(String buildingName,String floorName) {
        super("Floor with name: "+floorName+" in building: "+buildingName+" not found");
        log.error("Floor with name: "+floorName+" in building: "+buildingName+" not found");
    }
}

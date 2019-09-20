package com.api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.CONFLICT,reason = "Floor with that name already exists")
public class FloorAlreadyExistsException  extends  RuntimeException{

    public FloorAlreadyExistsException(String buildingName,String floorName) {
        super("Floor with name: "+floorName+" in building: "+buildingName+" already exists");
        log.error("Floor with name: "+floorName+" in building: "+buildingName+" already exists");
    }
}

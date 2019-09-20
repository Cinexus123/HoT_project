package com.api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.CONFLICT,reason = "Already exists.")
public class BuildingAlreadyExistsException extends RuntimeException {

    public BuildingAlreadyExistsException(String buildingName) {
        super("Building with name: "+buildingName+" already exists.");
        log.error("Building with name: "+buildingName+" already exists.");
    }


}

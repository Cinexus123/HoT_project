package com.api.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BuildingException extends RuntimeException {

    public BuildingException(String buildingName,String message, Exception ex) {
        super(message,ex);
        log.error("BuildingName: "+buildingName+". "+message);
    }

    public BuildingException(String buildingName, String floor, String message, Exception ex) {
        super(message,ex);
        log.error("Building name: "+buildingName+". Floor: "+floor+". "+message);
    }
}

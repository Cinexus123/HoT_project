package com.api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileStorageException extends RuntimeException {

    public FileStorageException(String s, Exception ex) {
        this.printStackTrace();
    }

    public FileStorageException(String s) {
        this.printStackTrace();
    }
}

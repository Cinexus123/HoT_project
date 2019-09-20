package com.api.controllers;

import com.api.config.properties.LogMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class LoginController {

    @PostMapping(path = "/login")
    public LoginResponse loginUser() {
        // Dummy request just to generate valid session cookie
        log.info(LogMessages.dummyLogin);
        return new LoginResponse(200, "Logged in");
    }

    @AllArgsConstructor
    @Data
    static class LoginResponse {
        Integer status;
        String message;
    }
}

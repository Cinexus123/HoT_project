package com.api.controllers;

import com.api.services.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RequestMapping("/email")
@RestController
public class EmailServerController {

    private final EmailService emailService;

    @GetMapping
    public ResponseEntity<Boolean> getStatus(){
        return ResponseEntity.ok(emailService.isReceiverRunning());
    }

    @PostMapping
    public ResponseEntity setStatus(Boolean status){
        log.info("EmailServer status set to {}",status);
        if(status)
            emailService.startReceiverServer();
        else
            emailService.stopReceiverServer();
        return ResponseEntity.ok().build();
    }
}

package com.api.controllers;

import com.api.commands.SettingsCommand;
import com.api.entities.Settings;
import com.api.services.impl.SettingsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/settings")
public class SettingController {

    private SettingsServiceImpl settingsService;

    @PostMapping("")
    public ResponseEntity<SettingsCommand> setSettings(@RequestBody SettingsCommand settingsCommand) {
        log.info("NEW SETTINGS RECEIVED: {}",settingsCommand);
        settingsService.setSettings(settingsCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public Settings getSettings() {
        return settingsService.getSettings();
    }


    @GetMapping("/logs")
    public List<String> getAllLogs(){
        return settingsService.getLogs();
    }


}

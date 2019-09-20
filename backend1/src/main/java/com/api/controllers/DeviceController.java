package com.api.controllers;

import com.api.commands.DeviceCommand;
import com.api.config.properties.LogMessages;
import com.api.entities.Device;
import com.api.services.DeviceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "/devices")
public class DeviceController {

    private final DeviceService devService;

    @GetMapping()
    public ResponseEntity<List<Device>> getAllDevices() {
        log.info(LogMessages.deviceGetAll);
        List<Device> devices = devService.getAll();
        return (devices.isEmpty()) ? ResponseEntity.noContent().build() : ResponseEntity.ok(devices);
    }

    @GetMapping(path = "/{nickName}")
    public ResponseEntity<List<Device>> getDevicesByOwner(@PathVariable("nickName") String nickName) {
        log.info(LogMessages.deviceGetByUser, nickName);
        List<Device> devices = devService.getAllByOwner(nickName);
        return (devices.isEmpty()) ? ResponseEntity.noContent().build() : ResponseEntity.ok(devices);
    }

    @PostMapping()
    public ResponseEntity<Device> saveDevice(@RequestBody DeviceCommand deviceCommand) {
        Device device = devService.saveDevice(
            new Device(
                null,
                deviceCommand.getMacAddress(),
                deviceCommand.getNickName(),
                deviceCommand.getDeviceName()
            ));
        log.info(LogMessages.devicePost, device, device.getNickName());
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Device> delete(@PathVariable Long id, @RequestParam(value = "user", required = false) String username) {
        Device device = devService.get(id);
        if (device == null) {
            return ResponseEntity.notFound().build();
        }
        if (username != null && !Objects.equals(device.getNickName(), username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(devService.delete(id));
    }

}

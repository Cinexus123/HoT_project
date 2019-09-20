package com.api.controllers;

import com.api.config.properties.LogMessages;
import com.api.entities.Room;
import com.api.services.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "/room")
public class RoomController {
    private final RoomService roomService;

    @PostMapping()
    public ResponseEntity<Room> saveRooms(@RequestBody Room room) {
        roomService.saveRooms(room);
        log.info(LogMessages.roomsPost, room.getBuilding(), room.getFloor(), room.getXPosition(), room.getYPosition());
        return ResponseEntity.ok(room);
    }

    @GetMapping()
    public ResponseEntity<List<Room>> getAllDevices() {
        log.info(LogMessages.roomsGetAll);
        List<Room> rooms = roomService.getAll();
        return (rooms.isEmpty()) ? ResponseEntity.noContent().build() : ResponseEntity.ok(rooms);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Room> delete(@PathVariable Long id) {
        log.info(LogMessages.deviceDelete, id);
        Room room = roomService.delete(id);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }
}

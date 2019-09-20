package com.api.controllers;

import com.api.commands.GoogleRoom;
import com.api.entities.Network;
import com.api.entities.Room;
import com.api.services.RocketResponseService;
import com.api.services.RoomService;
import com.api.services.SettingsService;
import com.api.services.impl.GoogleRoomsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.sqrt;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/rooms")

public class GoogleRoomsController {

    private GoogleRoomsServiceImpl googleRoomsService;
    private RocketResponseService rocketResponseService;
    private SettingsService settingsService;
    private RoomService roomService;

    @GetMapping(path = "")
    public ResponseEntity<String> getFreeRooms() {
        List<GoogleRoom> googleRoomList = googleRoomsService.getAll();
        StringBuilder response = new StringBuilder();
        for (GoogleRoom googleRoom : googleRoomList) {
            response.append(googleRoom.getName()).append("\n");
            if (googleRoom.getNextEventStartEpoch() == null) {
                response.append("There is no meeting in this room today\n");
            } else {
                Date checkDate = new Date();
                Date roomDate = new Date(googleRoom.getNextEventStartEpoch());
                if (roomDate.getTime() > checkDate.getTime()) {
                    response.append("Free until: ").append(roomDate.toString()).append("\n");
                }
                if (roomDate.getTime() < checkDate.getTime()) {
                    response.append("There is no meeting in this room today\n");
                }
            }
            response.append("\n");
        }
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @GetMapping("/freeRooms/{nickName}")
    public ResponseEntity<String> closestRooms(@PathVariable String nickName) {

        if (nickName.startsWith("@")) {
            nickName = nickName.substring(1);
        }

        Network network = null;
        List<Network> privateNetworks = rocketResponseService.getNetworksForUser(nickName, false);
        List<Network> businessNetworks = rocketResponseService.getNetworksForUser(nickName, false);
        if (settingsService.getSettings().getBusiness()) {
            if (!rocketResponseService.isBusinessEmpty(nickName)) {
                network = businessNetworks.get(0);
            } else if (!rocketResponseService.isPrivateEmpty(nickName)) {
                network = privateNetworks.get(0);
            }
        } else {
            if (!rocketResponseService.isPrivateEmpty(nickName)) {
                network = privateNetworks.get(0);
            } else if (!rocketResponseService.isBusinessEmpty(nickName)) {
                network = businessNetworks.get(0);
            }
        }
        if (network == null) return ResponseEntity.ok("");

        List<RoomWithDistance> roomWithDistanceList = new ArrayList<>();
        double distance;

        List<Room> roomsList = roomService.getAll();
        for (Room room : roomsList) {
            if (Objects.equals(room.getFloor(), network.getFloor())) {
               int Xvalue = (int) abs(room.getXPosition() - network.getXPos());
               int Yvalue = (int) abs(room.getYPosition() - network.getYPos());
                distance = sqrt(Xvalue * Xvalue + Yvalue * Yvalue);
                roomWithDistanceList.add(new RoomWithDistance(room , distance));

            }
        }
        for(Room room : roomsList) {
            if(!room.getFloor().equals(network.getFloor())) {
                distance = 100000;
                roomWithDistanceList.add(new RoomWithDistance(room , distance));
            }
        }
        Collections.sort(roomWithDistanceList);
        Collections.reverse(roomWithDistanceList);
        List<String> names = roomWithDistanceList.stream().map(RoomWithDistance::getRoom).map(Room::getName).collect(Collectors.toList());
        return new ResponseEntity<>(String.join(", ", names), HttpStatus.OK);

    }

    @Data
    @AllArgsConstructor
    static class RoomWithDistance implements Comparable<RoomWithDistance> {
        private Room room;
        private double distance;


        @Override
        public int compareTo(RoomWithDistance other) {
            return Double.compare(other.distance, this.distance);
        }
    }
}
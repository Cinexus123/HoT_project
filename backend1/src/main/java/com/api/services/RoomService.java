package com.api.services;

import com.api.entities.Room;

import java.util.List;

public interface RoomService {
    Room saveRooms(Room room);

    List<Room> getAll();

    Room delete(Long id);
    Room get(Long id);

    List<Room> getAllByBuildingAndFloor(String building,String floor);
    List<Room> getAllByBuilding(String building);
}

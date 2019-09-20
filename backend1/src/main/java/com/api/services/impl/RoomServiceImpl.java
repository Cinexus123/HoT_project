package com.api.services.impl;

import com.api.entities.Room;
import com.api.exceptions.NotFoundException;
import com.api.repositories.RoomRepository;
import com.api.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room saveRooms(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room get(Long id) {
        Optional<Room> roomsOptional = roomRepository.findById(id);
        if (roomsOptional.isEmpty()) {
            throw new NotFoundException();
        }
        return roomsOptional.get();
    }

    @Override
    public List<Room> getAllByBuildingAndFloor(String building, String floor) {
        return roomRepository.findAllByBuildingAndFloor(building,floor);
    }

    @Override
    public List<Room> getAllByBuilding(String building) {
        return roomRepository.findAllByBuilding(building);
    }

    @Override
   public List<Room> getAll() { return roomRepository.findAll(); }

    @Override
    public Room delete(Long id) {
        Room room = get(id);
        if (room == null) {
            return null;
        } else {
            roomRepository.delete(room);
            return room;
        }
    }



}

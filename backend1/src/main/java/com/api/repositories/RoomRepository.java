package com.api.repositories;

import com.api.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAll();

    List<Room> findAllByBuildingAndFloor(String building, String floor);

    List<Room> findAllByBuilding(String building);
}

package com.api.repositories;

import com.api.entities.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworkRepository extends JpaRepository<Network, Long> {

    Network findByName(String networkName);

    boolean existsByName(String name);

    List<Network> findAllByBuildingAndFloor(String building, String floor);

    List<Network> findAllByBuilding(String building);
}

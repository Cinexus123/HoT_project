package com.api.services;

import com.api.entities.Network;

import java.util.List;

public interface NetworkService {

    Network edit(Network network);

    Network get(Long id);

    List<Network> getAll();

    Network getByName(String networkName);

    boolean existsByName(String name);

    Network save(Network network);

    void delete(Long id);

    List<Network> getAllByBuildingAndFloor(String building,String floor);
    List<Network> getAllByBuilding(String building);
}

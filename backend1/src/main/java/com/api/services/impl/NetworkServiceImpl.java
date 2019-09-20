package com.api.services.impl;

import com.api.entities.Network;
import com.api.exceptions.NotFoundException;
import com.api.repositories.NetworkRepository;
import com.api.services.NetworkService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NetworkServiceImpl implements NetworkService {

    private final NetworkRepository networkRepository;

    @Override
    public Network edit(Network network) {
        if(network.getBuilding()==null){
            network.setBuilding("switch has no building set");
        }
        if(network.getFloor()==null){
            network.setFloor("switch has no floor set");
        }
        if(network.getDescription()==null){
            network.setDescription("switch description set");
        }
        if(network.getXPos()==null){
            network.setXPos(0L);
        }
        if(network.getYPos()==null){
            network.setYPos(0L);
        }
        return networkRepository.save(network);
    }

    @Override
    public Network get(Long id) {

        Optional<Network> optionalNetwork = networkRepository.findById(id);

        if (!optionalNetwork.isPresent()) {
            throw new NotFoundException();
        }

        return optionalNetwork.get();
    }

    @Override
    public List<Network> getAll() {
        return networkRepository.findAll();
    }

    @Override
    public Network getByName(String networkName) {
        return networkRepository.findByName(networkName);
    }

    @Override
    public boolean existsByName(String name) {
        return networkRepository.existsByName(name);
    }

    @Override
    public Network save(Network network) {
        return networkRepository.save(network);
    }

    @Override
    public void delete(Long id) {
        networkRepository.deleteById(id);
    }

    @Override
    public List<Network> getAllByBuildingAndFloor(String building, String floor) {
        return networkRepository.findAllByBuildingAndFloor(building,floor);
    }

    @Override
    public List<Network> getAllByBuilding(String building) {
        return networkRepository.findAllByBuilding(building);
    }
}

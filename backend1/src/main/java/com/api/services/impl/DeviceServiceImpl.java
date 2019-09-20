package com.api.services.impl;

import com.api.config.properties.NetworkDataProviderProperties;
import com.api.dto.NetworkFromProviderDto;
import com.api.entities.Device;
import com.api.exceptions.NotFoundException;
import com.api.repositories.DeviceRepository;
import com.api.services.DeviceService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final NetworkDataProviderProperties networkProvider;

    private RestTemplate restTemplate =  new RestTemplate();

    public DeviceServiceImpl(DeviceRepository deviceRepository, NetworkDataProviderProperties networkProvider) {
        this.deviceRepository = deviceRepository;
        this.networkProvider = networkProvider;
    }

    @Override
    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device get(Long id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            throw new NotFoundException();
        }
        return deviceOptional.get();
    }

    @Override
    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device delete(Long id) {
        Device device = get(id);
        if (device == null) {
            return null;
        } else {
            deviceRepository.delete(device);
            return device;
        }
    }


    @Override
    public Optional<Device> findByMacAddress(String macAddress) {
        return deviceRepository.findByMacAddress(macAddress);
    }

    @Override
    public List<Device> getAllByOwner(String nickName) {
        return deviceRepository.findAllByNickName(nickName);
    }

    @Override
    public NetworkFromProviderDto getDeviceFromProvider(String nickName) {
        return restTemplate.getForObject(networkProvider.getUrl() + nickName, NetworkFromProviderDto.class);

    }
}

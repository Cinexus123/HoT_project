package com.api.services;

import com.api.dto.NetworkFromProviderDto;
import com.api.entities.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceService {

    Device saveDevice(Device device);
    Device get(Long id);
    List<Device> getAll();
    Device delete(Long id);
    Optional<Device> findByMacAddress(String macAddress);
    List<Device> getAllByOwner(String nickName);
    NetworkFromProviderDto getDeviceFromProvider(String nickName);
}

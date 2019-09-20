package rakietrak.networkmock.services;

import rakietrak.networkmock.entities.Device;

import java.util.List;

public interface DeviceService {

    void updateDevice(Device newDevice);

    Device getDevice(String macAddress);
    List<Device> getAllDevices();

    Device getCurrentDevice(String mac);
    List<Device> getCurrentDevices(List<String> macs);

    void frequentlyUpdateAll();
}

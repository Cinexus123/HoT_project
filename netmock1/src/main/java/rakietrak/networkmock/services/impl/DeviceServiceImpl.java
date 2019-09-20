package rakietrak.networkmock.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rakietrak.networkmock.entities.Device;
import rakietrak.networkmock.exceptions.NotFoundException;
import rakietrak.networkmock.providers.DeviceEthProvider;
import rakietrak.networkmock.providers.DeviceWifiProvider;
import rakietrak.networkmock.repositories.DeviceRepository;
import rakietrak.networkmock.services.DeviceService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;
    private final DeviceEthProvider ethDeviceProvider;
    private final DeviceWifiProvider wifiDeviceProvider;

    @Override
    public void updateDevice(Device newDevice) {
        repository.save(newDevice);
    }

    @Override
    public Device getDevice(String macAddress) {
        return repository.findById(macAddress).orElseThrow(() -> new NotFoundException(macAddress));
    }

    @Override
    public List<Device> getAllDevices() {
        return repository.findAll();
    }

    @Override
    public Device getCurrentDevice(String mac) {
        ethDeviceProvider.provideOne(mac).ifPresent(this::updateDevice);
        wifiDeviceProvider.provideOne(mac).ifPresent(this::updateDevice);
        return getDevice(mac);
    }

    @Override
    public List<Device> getCurrentDevices(List<String> macs) {
        var devices = ethDeviceProvider.provideFew(macs);
        devices.addAll(wifiDeviceProvider.provideFew(macs));
        devices.forEach(this::updateDevice);
        return macs.stream().map(this::getDevice).collect(Collectors.toList());
    }

    @Scheduled(fixedRateString = "${updateFrequency}")
    @Override
    public void frequentlyUpdateAll() {
        log.info("Updating data using providers");
        var listOfOnlineDevices = ethDeviceProvider.provideAll();
        listOfOnlineDevices.addAll(wifiDeviceProvider.provideAll());

        listOfOnlineDevices.forEach(this::updateDevice);
        log.info("Finished updating data");
    }

}

package rakietrak.networkmock.providers.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rakietrak.networkmock.config.properties.EthDataProviderProperties;
import rakietrak.networkmock.dtos.DeviceEthCommand;
import rakietrak.networkmock.entities.Device;
import rakietrak.networkmock.providers.DeviceEthProvider;
import rakietrak.networkmock.utils.MacUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DeviceEthProviderImpl implements DeviceEthProvider {

    private RestTemplate template;
    private EthDataProviderProperties properties;

    public DeviceEthProviderImpl(EthDataProviderProperties properties) {
        this.properties = properties;
        this.template = new RestTemplate();
    }

    @Override
    public List<Device> provideAll() {
        log.info("Fetching new data from main switch");
        ResponseEntity<DeviceEthCommand[]> response;
        try {
            response = template.getForEntity(properties.getAddress(), DeviceEthCommand[].class);
        } catch (Exception ex) {
            log.error("Couldn't fetch data from main switch");
            ex.printStackTrace();
            return new ArrayList<>();
        }
        return Arrays.stream(response.getBody())
                .map(command -> new Device(MacUtils.prepareMacAddress(command.getMac_address()), command.getPort_id(), LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Device> provideFew(List<String> macs) {
        return provideAll().stream()
                .filter(device ->
                        macs.stream().anyMatch(mac -> device.getMacAddress().equals(mac)))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Device> provideOne(String mac) {
        return provideAll().stream()
                .filter(device -> device.getMacAddress().equals(mac))
                .findAny();
    }
}

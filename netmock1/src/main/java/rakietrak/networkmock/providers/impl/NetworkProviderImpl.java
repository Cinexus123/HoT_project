package rakietrak.networkmock.providers.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rakietrak.networkmock.config.properties.EthDataProviderProperties;
import rakietrak.networkmock.dtos.NetworkEthCommand;
import rakietrak.networkmock.dtos.NetworkWifiCommand;
import rakietrak.networkmock.entities.Device;
import rakietrak.networkmock.providers.NetworkProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
@Component
public class NetworkProviderImpl implements NetworkProvider {

    private final RestTemplate template = new RestTemplate();
    private EthDataProviderProperties properties;
    private final DeviceWifiProviderImpl provider;

    public NetworkProviderImpl(EthDataProviderProperties properties, DeviceWifiProviderImpl provider) {
        this.properties = properties;
        this.provider = provider;
    }

    public List<NetworkEthCommand> provideAll() {
        var list = getSwitches();
        getAPs().forEach(networkWifiCommand ->
                list.add(new NetworkEthCommand(networkWifiCommand.getAp_mac()))
        );
        return list;
    }

    public List<NetworkEthCommand> getSwitches() {
        log.info("Fetching new data about networks from main switch");
        ResponseEntity<NetworkEthCommand[]> response;
        try {
            response = template.getForEntity(properties.getAddress(), NetworkEthCommand[].class);
        } catch (Exception ex) {
            log.error("Couldn't fetch data from main switch");
            ex.printStackTrace();
            return new ArrayList<>();
        }
        return Arrays.stream(response.getBody()).distinct().collect(Collectors.toList());
    }

    public List<NetworkWifiCommand> getAPs() {
        return provider.provideAll().stream().map(Device::getNetworkId).distinct().map(NetworkWifiCommand::new).collect(Collectors.toList());
    }

}

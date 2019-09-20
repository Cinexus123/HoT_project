package rakietrak.networkmock.providers;

import rakietrak.networkmock.entities.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceWifiProvider {
    List<Device> provideAll();
    List<Device> provideFew(List<String> macs);
    Optional<Device> provideOne(String mac);
}

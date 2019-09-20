package rakietrak.networkmock.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rakietrak.networkmock.dtos.DeviceDto;
import rakietrak.networkmock.dtos.NetworkEthCommand;
import rakietrak.networkmock.entities.Device;
import rakietrak.networkmock.services.DeviceService;
import rakietrak.networkmock.services.NetworkService;
import rakietrak.networkmock.services.UserService;
import rakietrak.networkmock.utils.MacUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/")
@Slf4j
@AllArgsConstructor
public class RestNetworkController {

    private DeviceService deviceService;
    private UserService userService;
    private NetworkService networkService;

    @GetMapping("networks")
    public ResponseEntity<List<NetworkEthCommand>> getNetworks() {
        log.info("Getting current list of switches");
        return ResponseEntity.ok(networkService.getAll());
    }

    @GetMapping("networks/{macAddress}")
    public ResponseEntity<DeviceDto> getDeviceInfoByMac(@PathVariable String macAddress) {
        String finalMacAddress = MacUtils.prepareMacAddress(macAddress);
        log.info("Getting info about device : {}", finalMacAddress);

        //If given mac address is assigned to any user returns code 404
        //** Security reasons **
        if (userService.getAll().stream()
                .flatMap(user -> user.getDevices().stream())
                .map(Device::getMacAddress)
                .anyMatch(mac -> mac.equals(finalMacAddress))
        )
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(convertToDto(deviceService.getCurrentDevice(finalMacAddress)));
    }

    @GetMapping("networks/user/{nickName}")
    public ResponseEntity<List<DeviceDto>> getDeviceConnectionsInfoByUser(@PathVariable String nickName) {
        log.info("Getting list of {}'s devices", nickName);
        var devices = userService.get(nickName).getDevices();
        devices = deviceService.getCurrentDevices(
                devices.stream()
                        .map(Device::getMacAddress)
                        .collect(Collectors.toList())
        );
        return devices.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(devices.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    private DeviceDto convertToDto(Device device) {
        var dto = new DeviceDto();
        dto.setApName(device.getNetworkId());

        // If AP_name is mac address then AP is wifi, otherwise switch
        if (dto.getApName() != null)
            dto.setApType((MacUtils.isValid(device.getNetworkId()) ? "Wifi" : "Eth"));

        // if last-seen timestamp is older then 5 min sets online status to false
        if (device.getUpdatedAt() != null) {
            dto.setLastSeen(Timestamp.valueOf(device.getUpdatedAt()));
            var now = LocalDateTime.now();
            var toBeOutdated = device.getUpdatedAt().plus(5, ChronoUnit.MINUTES);
            dto.setOnline(now.isBefore(toBeOutdated));
        } else
            dto.setOnline(false);

        return dto;
    }
}

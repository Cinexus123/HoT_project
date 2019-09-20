package rakietrak.networkmock.providers.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rakietrak.networkmock.config.properties.UnifiProperties;
import rakietrak.networkmock.dtos.DeviceWifiCommand;
import rakietrak.networkmock.entities.Device;
import rakietrak.networkmock.providers.DeviceWifiProvider;
import rakietrak.networkmock.utils.MacUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DeviceWifiProviderImpl implements DeviceWifiProvider {

    private UnifiProperties unifi;
    private ObjectMapper mapper;
    private Runtime runtime;

    public DeviceWifiProviderImpl(UnifiProperties unifi) throws IOException, InterruptedException {
        this.unifi = unifi;
        this.mapper = new ObjectMapper();
        this.runtime = Runtime.getRuntime();

        // Authorization
        String data = mapper.writeValueAsString(new LoginData(
                unifi.getLogin(),
                unifi.getPassword(),
                true
        ));
        log.info("DATA: {}", data);
        String response = runCommand(runtime, "curl -v -k --data " + data + " -H \"Content-Type: application/json\" " + unifi.getAddress() + "/api/login"
                + " -c " + unifi.getTokenPath());
        log.info("UNIFI LOGIN: {}", response);
    }

    @Override
    public List<Device> provideAll() {
        log.info("Fetching new data from wifi");
        String response = null;
        try {
            response = runCommand(runtime, "curl -v -X GET -b " + unifi.getTokenPath() + " -k " + unifi.getAddress() + "/api/s/default/stat/sta");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        log.info("UNIFI DEVICES: {}", response);

        List<Device> devices;
        try {
            JsonNode node = mapper.readTree(response);
            DeviceWifiCommand[] deviceWifiCommands = mapper.convertValue(node.get("data"), DeviceWifiCommand[].class);
            devices = Arrays.stream(deviceWifiCommands)
                    .map(dev -> new Device(MacUtils.prepareMacAddress(dev.getMac()), dev.getAp_mac(),
                            LocalDateTime.ofEpochSecond(dev.get_last_seen_by_uap(), 0, ZoneOffset.ofHours(2))))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Fetching device from Unifi failed.");
            e.printStackTrace();
            devices = new ArrayList<>();
        }
        log.info("{} devices fetched from unifi", devices.size());
        return devices;
    }

    @Override
    public List<Device> provideFew(List<String> macs) {
        return provideAll().stream()
                .filter(dev ->
                        macs.stream()
                                .anyMatch(mac -> dev.getMacAddress().equals(mac))
                ).collect(Collectors.toList());
    }

    @Override
    public Optional<Device> provideOne(String mac) {
        return provideAll().stream()
                .filter(dev -> dev.getMacAddress().equals(mac))
                .findAny();
    }

    private String runCommand(Runtime runtime, String command) throws IOException, InterruptedException {
        Process process = runtime.exec(command);
        log.info("PID: {}", process.pid());
        StringBuilder result = new StringBuilder();

        inheritIO(process.getErrorStream(), System.err);

        new Thread(() -> {
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            try {
                while ((line = input.readLine()) != null)
                    result.append(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        process.waitFor();

        return result.toString();
    }

    private static void inheritIO(final InputStream src, final PrintStream dest) {
        new Thread(() -> {
            Scanner sc = new Scanner(src);
            while (sc.hasNextLine()) {
                dest.println(sc.nextLine());
            }
        }).start();
    }

    @AllArgsConstructor
    @Data
    static class LoginData {
        String username;
        String password;
        Boolean remember;
    }
}

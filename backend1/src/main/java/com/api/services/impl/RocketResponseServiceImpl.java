package com.api.services.impl;

import com.api.commands.NetworkServerCommand;
import com.api.config.properties.ServerConfigProperties;
import com.api.entities.Device;
import com.api.entities.Network;
import com.api.services.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RocketResponseServiceImpl implements RocketResponseService {
    private final MeetingInfoService meetingInfoService;
    private final NetworkService networkService;
    private final DeviceService deviceService;
    private final NetworkServerProvider networkServerProvider;
    private final ServerConfigProperties serverConfigProperties;

    @Override
    public String googleMeeting(String nickName) {
        StringBuilder responseBuilder = new StringBuilder();
        meetingInfoService.getMeetingByNickName(nickName).forEach(meeting ->
            responseBuilder.append("Meeting: ").append(meeting).append("\n")
        );

        return responseBuilder.toString();
    }

    @Override
    public String mapBusinessDevices(String nickName) {
        List<NetworkServerCommand> networks = networkServerProvider.getNetworksConnectedToUser(nickName);

        StringBuilder responseBuilder = new StringBuilder();
        List<String> urlOfSwitches = urlSwitchesList(nickName, networks, serverConfigProperties.getMapPath());
        urlOfSwitches.forEach(url ->
            responseBuilder.append("Link to position on map (business device): ").append(url).append("\n")
        );
        log.info("Sent list of map URLs with location of user business devices: " + nickName); //FIXME przenieść log do miejsca logowania

        return responseBuilder.toString();
    }

    @Override
    public String mapWithDescBusinessDevices(String nickName) {
        List<NetworkServerCommand> networks = networkServerProvider.getNetworksConnectedToUser(nickName);

        StringBuilder responseBuilder = new StringBuilder();
        List<String> urlOfSwitches = urlSwitchesList(nickName, networks, serverConfigProperties.getMapWithDescriptionPath());
        urlOfSwitches.forEach(url ->
            responseBuilder.append("Link to position on map (business device): ").append(url).append("\n")
        );
        log.info("Sent list of map with description URLs with location of user business devices: " + nickName); //FIXME przenieść log do miejsca logowania

        return responseBuilder.toString();
    }

    @Override
    public String descTxtBusinessDevices(String nickName) {
        List<NetworkServerCommand> networks = networkServerProvider.getNetworksConnectedToUser(nickName);

        log.info("Sent list of descriptions with location of user business devices: " + nickName);  //FIXME przenieść log do miejsca logowania

        return userDescriptionBuilder(nickName, networks).toString();
    }

    @Override
    public String mapPrivateDevices(String nickName) {

        StringBuilder responseBuilder = new StringBuilder();
        List<String> urlOfSwitches = urlSwitchesList(nickName, userPrivateDeviceListFromNetworkProvider(nickName), serverConfigProperties.getMapPath());
        urlOfSwitches.forEach(url ->
            responseBuilder.append("Link to position on map (private device): ").append(url).append("\n")
        );
        log.info("Sent list of map URLs with location of user private devices: " + nickName); //FIXME przenieść log do miejsca logowania

        return responseBuilder.toString();
    }

    @Override
    public String mapWithDescPrivateDevices(String nickName) {

        StringBuilder responseBuilder = new StringBuilder();
        List<String> urlOfSwitches = urlSwitchesList(nickName, userPrivateDeviceListFromNetworkProvider(nickName), serverConfigProperties.getMapWithDescriptionPath());
        urlOfSwitches.forEach(url ->
            responseBuilder.append("Link to position on map (private device): ").append(url).append("\n")
        );
        log.info("Sent list of map with description URLs with location of user private devices: " + nickName); //FIXME przenieść log do miejsca logowania

        return responseBuilder.toString();
    }

    @Override
    public String descTxtPrivateDevices(String nickName) {
        log.info("Sent list of descriptions with location of user business devices: " + nickName);  //FIXME przenieść log do miejsca logowania

        return userDescriptionBuilder(nickName, userPrivateDeviceListFromNetworkProvider(nickName)).toString();
    }

    @Override
    public Boolean isPrivateEmpty(String nickName) {
        return deviceService.getAllByOwner(nickName).isEmpty();
    }

    @Override
    public Boolean isBusinessEmpty(String nickName) {
        return !networkServerProvider.networkForUserExists(nickName);
    }

    public List<Network> getNetworksForUser(String nickName, Boolean business) {
        return (business
            ? networkServerProvider.getNetworksConnectedToUser(nickName)
            : userPrivateDeviceListFromNetworkProvider(nickName))
            .stream()
            .map(NetworkServerCommand::getApName)
            .map(networkService::getByName)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private Network networkServerCommmandToNetwork(NetworkServerCommand command) {
        if (command == null) return null;
        return networkService.getByName(command.getApName());
    }

    private List<String> urlSwitchesList(String nickName, List<NetworkServerCommand> networkServerCommandList, String urlEnd) {

        return networkServerCommandList.stream()
            .map(networkFromProvider -> {
                if (networkFromProvider == null) {
                    log.error("No data from network provider for element"); //FIXME dodać do logowni
                    return "No data from network provider for element";
                }
                Network network = networkService.getByName(networkFromProvider.getApName());
                if (network == null) {
                    log.error("No such switch name in database."); //FIXME dodać do logowni
                    return "Not valid switch name";
                } else {
                    String time = timeStamp(networkServerCommandList.get(0));
                    return "http://" + serverConfigProperties.getHostname() + ":" + serverConfigProperties.getPort() +
                        serverConfigProperties.getPath() +
                        "/networks/" + nickName + "/" + network.getId() + "/" + time + urlEnd; //FIXME timestamp ustawić
                }
            }).collect(Collectors.toList());
    }

    private List<NetworkServerCommand> userPrivateDeviceListFromNetworkProvider(String nickName) {
        List<Device> deviceList = deviceService.getAllByOwner(nickName);
        return deviceList.stream()
            .map(Device::getMacAddress)
            .map(networkServerProvider::getNetwork)
            .collect(Collectors.toList());
    }

    private StringBuilder userDescriptionBuilder(String nickName, List<NetworkServerCommand> userDevicesFromNetworkProvider) {
        StringBuilder responseBuilder = new StringBuilder();
        try {
            userDevicesFromNetworkProvider
                .forEach(networkServerCommand -> {
                    Network network = networkService.getByName(networkServerCommand.getApName());
                    responseBuilder.append("Description of user location:").append("\n");
                    responseBuilder.append("Nick: ").append(nickName).append("\n");
                    responseBuilder.append("Building: ").append(network.getBuilding()).append("\n");
                    responseBuilder.append("Floor: ").append(network.getFloor().substring(0, network.getFloor().length() - 4)).append("\n");
                    responseBuilder.append("Description: ").append(network.getDescription()).append("\n");
                    responseBuilder.append("Timestamp: ").append(timeStamp(networkServerCommand).replace("T", " ")).append("\n");
                    responseBuilder.append(" ").append("\n");
                });
        } catch (NullPointerException e) {
            responseBuilder.append("Description of user location:").append(" Device not connected.").append("\n");
            log.error("Device not connected"); //FIXME dodać do logowni
        }
        return responseBuilder;
    }

    private String timeStamp(NetworkServerCommand networkServerCommand) {
        String timeFromNetworkServerCommand = networkServerCommand.getLastSeen();
        if (timeFromNetworkServerCommand == null) {
            return "never seen before";
        }
        String time = timeFromNetworkServerCommand.substring(0, timeFromNetworkServerCommand.length() - 8);
        Instant now = Instant.parse(time.concat("000Z"));
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(now, ZoneId.of("Europe/Warsaw"));
        time = dateTime.toString();
        return time.substring(0, time.length() - 21);
    }

}

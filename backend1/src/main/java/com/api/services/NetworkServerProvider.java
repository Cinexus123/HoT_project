package com.api.services;

import com.api.commands.NetworkServerCommand;

import java.util.List;

public interface NetworkServerProvider {

    NetworkServerCommand getNetwork(String macAddress);

    List<NetworkServerCommand> getNetworksConnectedToUser(String userName);

    Boolean networkForUserExists(String userName);


}

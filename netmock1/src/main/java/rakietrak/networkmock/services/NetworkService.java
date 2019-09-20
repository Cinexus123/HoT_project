package rakietrak.networkmock.services;

import rakietrak.networkmock.dtos.NetworkEthCommand;

import java.util.List;

public interface NetworkService {
    List<NetworkEthCommand> getAll();
}

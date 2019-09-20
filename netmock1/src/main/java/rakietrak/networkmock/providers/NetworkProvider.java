package rakietrak.networkmock.providers;

import rakietrak.networkmock.dtos.NetworkEthCommand;

import java.util.List;

public interface NetworkProvider {
    List<NetworkEthCommand> provideAll();
}

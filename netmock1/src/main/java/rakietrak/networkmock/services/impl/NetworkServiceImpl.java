package rakietrak.networkmock.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rakietrak.networkmock.dtos.NetworkEthCommand;
import rakietrak.networkmock.providers.NetworkProvider;
import rakietrak.networkmock.services.NetworkService;

import java.util.List;

@Service
@AllArgsConstructor
public class NetworkServiceImpl implements NetworkService {

    private final NetworkProvider provider;

    @Override
    public List<NetworkEthCommand> getAll() {
        return provider.provideAll();
    }
}

package com.api.services.impl;

import com.api.commands.NetworkServerCommand;
import com.api.config.properties.NetworkDataProviderProperties;
import com.api.services.NetworkServerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class NetworkServerProviderImpl implements NetworkServerProvider {
    private final NetworkDataProviderProperties providerProperties;
    private final RestTemplate restTemplate;

    public NetworkServerProviderImpl(NetworkDataProviderProperties providerProperties) {
        this.providerProperties = providerProperties;
        this.restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(
                new BasicAuthenticationInterceptor(providerProperties.getAuthUser(), providerProperties.getAuthPassword())
        );
    }

    @Override
    public NetworkServerCommand getNetwork(String macAddress) {
        try {
            return restTemplate.exchange(providerProperties.getUrl() + macAddress, HttpMethod.GET,
                    null, NetworkServerCommand.class).getBody();
        } catch (HttpClientErrorException e) {
            log.error("error from mock server get network"); //FIXME dodać log do logowni
            return null;
        }
    }

    @Override
    public List<NetworkServerCommand> getNetworksConnectedToUser(String userName) {
        try {
            var response = restTemplate.exchange((providerProperties.getUrl() + "/user/" + userName), HttpMethod.GET,
                    null, NetworkServerCommand[].class).getBody();
            return response != null ?
                    Arrays.asList(response)
                    : new ArrayList<>();
        } catch (HttpClientErrorException e) {
            log.error("error from mock server get networks"); //FIXME dodać log do logowni
            return new ArrayList<>();
        }
    }

    @Override
    public Boolean networkForUserExists(String userName) { //FIXME: WTF is this function for ?
        return getNetworksConnectedToUser(userName).size() > 0;
    }
}


package com.api.component;

import com.api.commands.NetworkServerNameCommand;
import com.api.config.properties.LogMessages;
import com.api.config.properties.NetworkDataProviderProperties;
import com.api.entities.Network;
import com.api.services.NetworkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
public class ScheduledTasks {

    private final NetworkDataProviderProperties providerProperties;
    private final NetworkService networkService;

    @Scheduled(fixedRate = 600_000)
    public void getNetworks() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(
                new BasicAuthenticationInterceptor(
                        providerProperties.getAuthUser(), providerProperties.getAuthPassword()
                )
        );

        List<NetworkServerNameCommand> list;
        try {
            log.info("Sending get request for list of networks to {}", providerProperties.getUrl());
            list = restTemplate.exchange(
                    providerProperties.getUrl(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<NetworkServerNameCommand>>() {
                    }).getBody();
        } catch (RestClientException ex) {
            log.error(LogMessages.networksFetchFailed);
            ex.printStackTrace();
            return;
        }

        if (list == null) {
            log.error(LogMessages.networksFetchFailed);
            return;
        }

        List<String> networks = list.stream()
                .map(NetworkServerNameCommand::getPort_id)
                .filter(name -> !networkService.existsByName(name))
                .collect(Collectors.toList());

        networks.forEach(networkName -> {
            Network network = new Network();
            network.setName(networkName);
            networkService.edit(network);
        });

        log.info(LogMessages.networksAdded, networks);
    }
}

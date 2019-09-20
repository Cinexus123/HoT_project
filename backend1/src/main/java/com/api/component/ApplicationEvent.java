package com.api.component;

import com.api.config.properties.ServerConfigProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ApplicationEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final ServerConfigProperties serverConfigProperties;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        log.info("Application is running on http://" + serverConfigProperties.getHostname() + ":" + serverConfigProperties.getPort() + serverConfigProperties.getPath());
    }
}

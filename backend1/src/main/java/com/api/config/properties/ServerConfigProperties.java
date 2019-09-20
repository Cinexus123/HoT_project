package com.api.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "server-info")
public class ServerConfigProperties {

    @Value("${server.port}")
    private Integer port;
    private String hostname;
    private String path;

    private String mapPath;
    private String mapWithDescriptionPath;
}

package com.api.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "network-provider")
public class NetworkDataProviderProperties {
    private String ip;
    private String port;
    private String url;
    private String authUser;
    private String authPassword;
}

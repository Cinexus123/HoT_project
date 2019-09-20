package com.api.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "imap")
public class IMAPEmailServerProperties {

    private String host;
    private String port;
    private String username;
    private String password;
    private String protocol;
    private String folder;
    private boolean enable;
    private String fullImapAddress;
}

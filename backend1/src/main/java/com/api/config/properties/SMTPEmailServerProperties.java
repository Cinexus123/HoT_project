package com.api.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@Data
@ConfigurationProperties(prefix = "smtp")
public class SMTPEmailServerProperties {

    //Sender
    private String host;
    private String port;
    private String username;
    private String password;
    private String from;
    private String transportProtocol;
    private String smtpAuth;
    private String smtpStarttlsEnable;
    private String debug;
    private String socketFactoryPort;
    private String socketFactoryClass;
}

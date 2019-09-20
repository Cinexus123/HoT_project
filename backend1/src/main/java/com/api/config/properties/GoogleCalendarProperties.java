package com.api.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Data
@ConfigurationProperties(prefix = "google-calendar")
public class GoogleCalendarProperties {
    private String address;
    private String meetingsUrl;
    private String roomsUrl;
    private String user;
    private String password;
}

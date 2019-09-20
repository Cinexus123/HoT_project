package com.api.services.impl;

import com.api.commands.GoogleRoom;
import com.api.config.properties.GoogleCalendarProperties;
import com.api.services.GoogleRoomsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.HttpsURLConnection;
import java.util.List;

@Slf4j
@Service
@CrossOrigin
public class GoogleRoomsServiceImpl implements GoogleRoomsService {

    private GoogleCalendarProperties properties;
    private RestTemplate restTemplate = new RestTemplate();

    public GoogleRoomsServiceImpl(GoogleCalendarProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<GoogleRoom> getAll() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> hostname.equals("nocnica.ncdc"));
            restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(properties.getUser(), properties.getPassword()));
            return restTemplate.exchange((properties.getRoomsUrl()), HttpMethod.GET, null, new ParameterizedTypeReference<List<GoogleRoom>>() {
            }).getBody();
        } catch (Exception e) {
            log.error("Google Rooms Service", e);
            return null;
        }
    }
}
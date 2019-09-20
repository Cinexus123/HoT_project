package com.api.services.impl;

import com.api.config.properties.GoogleCalendarProperties;
import com.api.services.MeetingInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
public class MeetingInfoServiceImpl implements MeetingInfoService {

    private GoogleCalendarProperties googleCalendarProperties;
    private RestTemplate restTemplate = new RestTemplate();

    public MeetingInfoServiceImpl(GoogleCalendarProperties googleCalendarProperties) {
        this.googleCalendarProperties = googleCalendarProperties;
    }


    @Override
    public List<String> getMeetingByNickName(String nickName) {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> hostname.equals("nocnica.ncdc"));
            restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(googleCalendarProperties.getUser(), googleCalendarProperties.getPassword()));
            var list = restTemplate.getForObject(googleCalendarProperties.getMeetingsUrl() + nickName, String[].class);
            if (list == null) {
                log.error("list is null");
                return null;
            }
            log.info(Arrays.toString(list));
            return Arrays.asList(list);
        } catch (Exception e) {
            List<String> lista = new ArrayList<>();
            lista.add("Currently the person has no meeting.");
            log.error("Currently the person has no meeting.");
            return lista;
        }

    }


}

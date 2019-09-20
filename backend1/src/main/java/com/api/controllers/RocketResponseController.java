package com.api.controllers;

import com.api.commands.NetworkServerCommand;
import com.api.services.DeviceService;
import com.api.services.NetworkServerProvider;
import com.api.services.RocketResponseService;
import com.api.services.SettingsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class RocketResponseController {

    private final NetworkServerProvider networkServerProvider;
    private final RocketResponseService rocketResponseService;
    private final SettingsService settingsService;

    @GetMapping("/{nickName}")
    public ResponseEntity<String> defaultResponseWhereIsUser(@PathVariable("nickName") String nickName) {

        if (nickName.startsWith("@")) {
            nickName = nickName.substring(1);
        }

        Boolean business = settingsService.getSettings().getBusiness();
        Boolean googleMeeting = settingsService.getSettings().getGoogleMeetings();
        Boolean map = settingsService.getSettings().getMap();
        Boolean mapWithDesc = settingsService.getSettings().getDescMap();
        Boolean descTxt = settingsService.getSettings().getDescTxt();

        StringBuilder responseBuilder = new StringBuilder();
        
        if (googleMeeting) {
            responseBuilder.append(rocketResponseService.googleMeeting(nickName));
        }

        if (business) {
            // -------- start of business device response -------
            if (map) {
                responseBuilder.append(rocketResponseService.mapBusinessDevices(nickName));
            }
            if (mapWithDesc) {
                responseBuilder.append(rocketResponseService.mapWithDescBusinessDevices(nickName));
            }
            if (descTxt) {
                responseBuilder.append(rocketResponseService.descTxtBusinessDevices(nickName));
            }
            // -------- end of business device response -------
            // -------- start of private device response -------
            if (map) {
                responseBuilder.append(rocketResponseService.mapPrivateDevices(nickName));
            }
            if (mapWithDesc) {
                responseBuilder.append(rocketResponseService.mapWithDescPrivateDevices(nickName));
            }
            if (descTxt) {
                responseBuilder.append(rocketResponseService.descTxtPrivateDevices(nickName));
            }
            // -------- end of private device response -------
        } else {
            // -------- start of private device response -------
            if (map) {
                responseBuilder.append(rocketResponseService.mapPrivateDevices(nickName));
            }
            if (mapWithDesc) {
                responseBuilder.append(rocketResponseService.mapWithDescPrivateDevices(nickName));
            }
            if (descTxt) {
                responseBuilder.append(rocketResponseService.descTxtPrivateDevices(nickName));
            }
            // -------- end of private device response -------
            // -------- start of business device response -------
            if (map) {
                responseBuilder.append(rocketResponseService.mapBusinessDevices(nickName));
            }
            if (mapWithDesc) {
                responseBuilder.append(rocketResponseService.mapWithDescBusinessDevices(nickName));
            }
            if (descTxt) {
                responseBuilder.append(rocketResponseService.descTxtBusinessDevices(nickName));
            }
            // -------- end of business device response -------

        }

        if(rocketResponseService.isPrivateEmpty(nickName)){
            responseBuilder.append("No private devices for user: ").append(nickName).append("\n");
        }

        if(rocketResponseService.isBusinessEmpty(nickName)){
            responseBuilder.append("No business devices for user: ").append(nickName).append("\n");
        }

        return new ResponseEntity<>(responseBuilder.toString(), HttpStatus.OK);  //FIXME przenieść log do miejsca logowania
    }

}



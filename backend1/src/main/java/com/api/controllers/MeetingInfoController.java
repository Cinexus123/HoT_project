package com.api.controllers;

import com.api.services.MeetingInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "/meetingInfo/")
public class MeetingInfoController {

    private final MeetingInfoService meetingInfoService;

    @GetMapping(path = "{nickName}")
    public List<String> getMeetingByPerson(@PathVariable("nickName") String nickName) {
        log.info("Getting meeting info by user: " + nickName);
        return this.meetingInfoService.getMeetingByNickName(nickName);
    }
}

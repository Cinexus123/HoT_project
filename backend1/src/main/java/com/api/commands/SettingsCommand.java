package com.api.commands;

import lombok.Data;

@Data
public class SettingsCommand {

    private Boolean map;

    private Boolean userPhoto;

    private Boolean descTxt;

    private Boolean descMap;

    private Boolean googleMeetings;

    private Boolean date;

    private Boolean business;
}

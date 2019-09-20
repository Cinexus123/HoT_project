package com.api.dto;

import lombok.Data;

@Data
public class UserDeviceDto {

    private String nickName;
    private String deviceName;
    private String deviceType;
    private String building;
    private String floor;
    private String floorDescription;
    private String mapUrl;
}

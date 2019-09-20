package com.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {

    private Long id;
    private String macAddress;
    private Long networkID;
    private String nickName;
    private String deviceName;

}

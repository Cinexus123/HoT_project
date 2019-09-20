package com.api.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCommand {

    private String macAddress;
    private String nickName;
    private String deviceName;

}

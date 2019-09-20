package com.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkFromProviderDto {

    private String apType;
    private String apName;
    private String lastSeen;
    private Boolean online;
}

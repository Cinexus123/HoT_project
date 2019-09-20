package com.api.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetworkServerCommand {

    private String apName;
    private String apType;
    private String lastSeen;
    private Boolean online;

}

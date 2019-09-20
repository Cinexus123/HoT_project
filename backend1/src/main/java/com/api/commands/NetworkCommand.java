package com.api.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkCommand {

    private Long id;
    private String name;
    private String description;
    private Long x;
    private Long y;
    private String building;
    private String floor;
}

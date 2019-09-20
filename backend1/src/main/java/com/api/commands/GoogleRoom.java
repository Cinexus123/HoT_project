package com.api.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleRoom {
    private String name;
    private Integer floor;
    private Integer building;
    private Long nextEventStartEpoch;
}

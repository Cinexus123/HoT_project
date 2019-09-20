package com.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkDto {

    private Long id;
    private String name;
    private String description;
    private Long xPos;
    private Long yPos;
    private String building;
    private String floor;

}

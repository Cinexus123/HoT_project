package com.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Network {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true)
    private String name;
    private String description;
    private Long xPos;
    private Long yPos;
    private String building;
    private String floor;

    public Network(String name, String description, Long xPos, Long yPos, String building, String floor) {
        this.name = name;
        this.description = description;
        this.xPos = xPos;
        this.yPos = yPos;
        this.building = building;
        this.floor = floor;
    }
}

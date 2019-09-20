package com.api.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Settings {
    @Id
    private Long id;

    private Boolean map;

    private Boolean userPhoto;

    private Boolean descTxt;

    private Boolean descMap;

    private Boolean googleMeetings;

    private Boolean date;

    private Boolean business;

    public Settings(Boolean map, Boolean userPhoto, Boolean descTxt, Boolean descMap, Boolean googleMeetings, Boolean date, Boolean business) {
        this.map = map;
        this.userPhoto = userPhoto;
        this.descTxt = descTxt;
        this.descMap = descMap;
        this.googleMeetings = googleMeetings;
        this.date = date;
        this.business = business;
    }
}

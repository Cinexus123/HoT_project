package com.api.services;

import com.api.entities.Network;

import java.util.List;

public interface RocketResponseService {

    String googleMeeting(String nickName);

    String mapBusinessDevices(String nickName);

    String mapWithDescBusinessDevices(String nickName);

    String descTxtBusinessDevices(String nickName);

    String mapPrivateDevices(String nickName);

    String mapWithDescPrivateDevices(String nickName);

    String descTxtPrivateDevices(String nickName);

    List<Network> getNetworksForUser(String nickName, Boolean business);

    Boolean isPrivateEmpty(String nickName);

    Boolean isBusinessEmpty(String nickName);

}

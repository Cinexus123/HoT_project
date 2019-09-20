package com.api.services;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface MapDrawService {
    byte[] drawPointOnMapWithDescription(String nickName, String building, String floor, String description,
                                         Resource resource, Long x, Long y, String timestamp) throws IOException;
    byte[] drawPointOnMap(Resource resource, String nickName, Long x, Long y) throws IOException;
}

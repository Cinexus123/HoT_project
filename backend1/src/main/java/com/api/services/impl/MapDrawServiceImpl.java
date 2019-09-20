package com.api.services.impl;

import com.api.services.MapDrawService;
import com.api.utils.MapDrawer;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;

@Service
@AllArgsConstructor
public class MapDrawServiceImpl implements MapDrawService {

    @Override
    public byte[] drawPointOnMapWithDescription(String nickName, String building, String floor, String description,
                                                Resource resource, Long x, Long y, String timestamp) throws IOException {

        return new MapDrawer().open(resource.getFile().getPath())
            .drawDescription(nickName, building, floor, description, timestamp)
            .drawDot(x.intValue(), y.intValue(), nickName)
            .toByteArray();
    }

    @Override
    public byte[] drawPointOnMap(Resource resource, String nickName, Long x, Long y) throws IOException {
        return new MapDrawer().open(resource.getFile().getPath())
            .drawDot(x.intValue(), y.intValue(), nickName).toByteArray();
    }
}

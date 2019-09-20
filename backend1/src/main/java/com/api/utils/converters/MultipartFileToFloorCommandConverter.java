package com.api.utils.converters;

import com.api.commands.FloorCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileToFloorCommandConverter implements Converter<MultipartFile, FloorCommand> {
    @Override
    public FloorCommand convert(MultipartFile source) {
        return new FloorCommand(source);
    }
}

package com.api.commands;

import com.api.validation.annotations.ValidFloor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorCommand {

    @ValidFloor(regex="^\\w+.png$")
    private MultipartFile file;
}

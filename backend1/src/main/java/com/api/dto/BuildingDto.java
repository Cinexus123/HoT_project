package com.api.dto;

import com.api.models.Floor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDto {
    @Pattern(regexp = "^\\w+$")
    private String name;
    private List<Floor> floors;
}

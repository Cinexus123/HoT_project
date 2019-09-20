package com.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Floor {
    @Pattern(regexp = "^\\w+.\\w+$")
    private String name;
    private String image;
}

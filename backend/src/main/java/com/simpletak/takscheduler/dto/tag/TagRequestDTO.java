package com.simpletak.takscheduler.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDTO {
    @NotNull
    @Size(min = 2, max = 32)
    private String tagName;
}

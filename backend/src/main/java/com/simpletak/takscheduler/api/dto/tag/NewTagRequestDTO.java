package com.simpletak.takscheduler.api.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewTagRequestDTO {

    @NotNull
    @Size(min = 2, max = 32, message = "Tag name must be between 2 and 32 characters")
    private String tagName;
}

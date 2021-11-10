package com.simpletak.takscheduler.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDTO {

    private UUID tagId;

    @NotNull
    @Size(min = 2, max = 32, message = "Tag name must be between 2 and 32 characters")
    private String tagName;
}

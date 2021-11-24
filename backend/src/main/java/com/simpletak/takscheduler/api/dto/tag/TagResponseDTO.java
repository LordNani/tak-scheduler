package com.simpletak.takscheduler.api.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDTO {

    private UUID tagId;
    private String tagName;
}

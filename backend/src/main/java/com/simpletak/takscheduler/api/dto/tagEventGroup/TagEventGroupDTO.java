package com.simpletak.takscheduler.api.dto.tagEventGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class TagEventGroupDTO {
    private UUID id;
    private UUID tagID;
    private UUID eventGroupID;
}

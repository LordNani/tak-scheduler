package com.simpletak.takscheduler.api.dto.eventGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewEventGroupDTO {
    @NotNull
    private String eventName;
    @NotNull
    private String eventGroupDescription;
    @NotNull
    private UUID ownerId;
}

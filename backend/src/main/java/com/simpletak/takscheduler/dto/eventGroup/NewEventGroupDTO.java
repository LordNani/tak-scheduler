package com.simpletak.takscheduler.dto.eventGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewEventGroupDTO {
    @NotNull
    private String eventName;
    @NotNull
    private String eventGroupDescription;
    @NotNull
    private UUID ownerId;
}

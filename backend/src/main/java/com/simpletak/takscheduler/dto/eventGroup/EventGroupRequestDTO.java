package com.simpletak.takscheduler.dto.eventGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventGroupRequestDTO {
    @NotNull
    @Size(min = 2, max = 32)
    private String eventGroupName;

    private String eventGroupDescription;
}

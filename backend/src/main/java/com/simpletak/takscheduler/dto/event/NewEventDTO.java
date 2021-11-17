package com.simpletak.takscheduler.dto.event;
import com.simpletak.takscheduler.model.event.EventFreq;
import com.simpletak.takscheduler.model.event.EventPriority;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class NewEventDTO {
    @NotNull
    private String eventName;
    @NotNull
    private String eventDescription;
    @NotNull
    private EventPriority eventPriority;
    @NotNull
    private boolean isReoccurring;
    @NotNull
    private EventFreq eventFreq;
    @NotNull
    private Date eventDate;
    @NotNull
    private Date eventTime;
    @NotNull
    private UUID eventGroupId;
}

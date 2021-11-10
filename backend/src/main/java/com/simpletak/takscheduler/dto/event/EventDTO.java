package com.simpletak.takscheduler.dto.event;
import com.simpletak.takscheduler.model.event.EventFreq;
import com.simpletak.takscheduler.model.event.EventPriority;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EventDTO {
    private UUID id;
    private String eventName;
    private String eventDescription;
    private EventPriority eventPriority;
    private boolean isReoccurring;
    private EventFreq eventFreq;
    private Date eventDate;
    private Date eventTime;
}

package com.simpletak.takscheduler.dto.event;
import com.simpletak.takscheduler.model.event.EventFreq;
import com.simpletak.takscheduler.model.event.EventPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private UUID id;
    private String eventName;
    private String eventDescription;
    private EventPriority eventPriority;
    private boolean isReoccurring;
    private EventFreq eventFreq;
    private Date eventDate;
    private Date eventTime;
    private UUID eventGroupId;

    public EventDTO(NewEventDTO eventDTO, UUID id) {
        this.id = id;
        this.eventDate = eventDTO.getEventDate();
        this.eventTime = eventDTO.getEventTime();
        this.eventFreq = eventDTO.getEventFreq();
        this.isReoccurring = eventDTO.isReoccurring();
        this.eventPriority = eventDTO.getEventPriority();
        this.eventName = eventDTO.getEventName();
        this.eventDescription = eventDTO.getEventDescription();
        this.eventGroupId = eventDTO.getEventGroupId();
    }
}

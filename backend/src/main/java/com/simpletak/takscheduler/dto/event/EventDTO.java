package com.simpletak.takscheduler.dto.event;
import com.simpletak.takscheduler.model.event.EventFreq;
import com.simpletak.takscheduler.model.event.EventPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
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
    @NotNull
    private UUID id;
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

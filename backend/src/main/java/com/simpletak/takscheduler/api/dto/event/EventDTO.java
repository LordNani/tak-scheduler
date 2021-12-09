package com.simpletak.takscheduler.api.dto.event;
import com.simpletak.takscheduler.api.model.event.EventFreq;
import com.simpletak.takscheduler.api.model.event.EventPriority;
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
        this.nextEventDate = eventDTO.getNextEventDate();
//        this.eventTime = eventDTO.getEventTime();
        this.eventFreq = eventDTO.getEventFreq();
        this.isReoccurring = eventDTO.isReoccurring();
        this.eventPriority = eventDTO.getEventPriority();
        this.eventName = eventDTO.getEventName();
        this.eventDescription = eventDTO.getEventDescription();
        this.eventGroupId = eventDTO.getEventGroupId();
        if(eventDTO.isReoccurring()){
            this.startEventDate = eventDTO.getNextEventDate();
            this.endEventDate = eventDTO.getEndEventDate();
        }
        else{
            this.startEventDate = eventDTO.getNextEventDate();
            this.endEventDate = eventDTO.getNextEventDate();
        }
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
    private Date nextEventDate;
//    @NotNull
//    private Date eventTime;
    @NotNull
    private UUID eventGroupId;
    @NotNull
    private Date startEventDate;
    @NotNull
    private Date endEventDate;

}

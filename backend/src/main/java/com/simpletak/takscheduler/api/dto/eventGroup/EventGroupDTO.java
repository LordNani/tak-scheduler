package com.simpletak.takscheduler.api.dto.eventGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventGroupDTO{

    public EventGroupDTO(NewEventGroupDTO base, UUID id){
        this.eventGroupDescription = base.getEventGroupDescription();
        this.eventName = base.getEventName();
        this.ownerId = base.getOwnerId();
        this.id = id;
    }

    @NotNull
    private String eventName;
    @NotNull
    private String eventGroupDescription;
    @NotNull
    private UUID ownerId;
    @NotNull
    private UUID id;
}

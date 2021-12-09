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
public class EventGroupDTO{

    public EventGroupDTO(NewEventGroupDTO base, UUID id, UUID ownerId){
        this.eventGroupDescription = base.getEventGroupDescription();
        this.eventGroupName = base.getEventGroupName();
        this.ownerId = ownerId;
        this.id = id;
    }

    @NotNull
    private String eventGroupName;
    @NotNull
    private String eventGroupDescription;
    @NotNull
    private UUID ownerId;
    @NotNull
    private UUID id;

    private boolean isOwned;
    private boolean isSubscribed;
}

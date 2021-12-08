package com.simpletak.takscheduler.api.model.event.scheduling;

import lombok.Data;

import java.util.UUID;

@Data
public class EventSchedulingByCronDTO {
    private UUID eventID;
    private String executionCronDate;
}

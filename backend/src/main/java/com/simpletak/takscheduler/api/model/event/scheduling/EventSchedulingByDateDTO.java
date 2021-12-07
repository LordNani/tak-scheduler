package com.simpletak.takscheduler.api.model.event.scheduling;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class EventSchedulingByDateDTO {
    private UUID eventID;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date executionDate;
}

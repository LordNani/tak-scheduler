package com.simpletak.takscheduler.api.exception.event;

import com.simpletak.takscheduler.api.exception.WebException;
import org.springframework.http.HttpStatus;

public class EventNotFoundException extends WebException {

    public EventNotFoundException() {
        super("Event with this id not found", HttpStatus.NOT_FOUND);
    }

}

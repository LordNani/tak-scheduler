package com.simpletak.takscheduler.exception.event;

import com.simpletak.takscheduler.exception.WebException;
import org.springframework.http.HttpStatus;

public class EventNotFoundException extends WebException {

    public EventNotFoundException() {
        super("Event with this id not found", HttpStatus.NOT_FOUND);
    }

}

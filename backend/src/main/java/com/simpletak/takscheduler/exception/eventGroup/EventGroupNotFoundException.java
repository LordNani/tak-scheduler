package com.simpletak.takscheduler.exception.eventGroup;

import com.simpletak.takscheduler.exception.WebException;
import org.springframework.http.HttpStatus;

public class EventGroupNotFoundException extends WebException {

    public EventGroupNotFoundException() {
        super("EventGroup with this name not found", HttpStatus.NOT_FOUND);
    }

}

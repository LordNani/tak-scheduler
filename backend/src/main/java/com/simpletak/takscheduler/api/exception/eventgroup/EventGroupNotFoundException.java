package com.simpletak.takscheduler.api.exception.eventgroup;

import com.simpletak.takscheduler.api.exception.WebException;
import org.springframework.http.HttpStatus;

public class EventGroupNotFoundException extends WebException {

    public EventGroupNotFoundException() {
        super("EventGroup with this id not found", HttpStatus.NOT_FOUND);
    }

}

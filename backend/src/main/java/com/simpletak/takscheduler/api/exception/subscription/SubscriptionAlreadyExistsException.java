package com.simpletak.takscheduler.api.exception.subscription;

import com.simpletak.takscheduler.api.exception.WebException;
import org.springframework.http.HttpStatus;

public class SubscriptionAlreadyExistsException extends WebException {

    public SubscriptionAlreadyExistsException() {
        super("Such subscription already exists", HttpStatus.CONFLICT);
    }

}

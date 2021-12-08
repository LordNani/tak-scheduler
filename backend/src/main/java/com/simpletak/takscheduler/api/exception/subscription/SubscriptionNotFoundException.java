package com.simpletak.takscheduler.api.exception.subscription;

import com.simpletak.takscheduler.api.exception.WebException;
import org.springframework.http.HttpStatus;

public class SubscriptionNotFoundException extends WebException {
    public SubscriptionNotFoundException() {
        super("Not found subscription", HttpStatus.NOT_FOUND);
    }
}

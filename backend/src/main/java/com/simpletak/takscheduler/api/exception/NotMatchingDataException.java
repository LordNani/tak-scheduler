package com.simpletak.takscheduler.api.exception;

import org.springframework.http.HttpStatus;

public class NotMatchingDataException extends WebException {

    public NotMatchingDataException() {
        super("Pars of a data in request doesn't match", HttpStatus.CONFLICT);
    }

}

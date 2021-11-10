package com.simpletak.takscheduler.exception;

import org.springframework.http.HttpStatus;

public class NotMatchingDataException extends WebException {

    public NotMatchingDataException() {
        super("Pars of a data in request doesn't match", HttpStatus.CONFLICT);
    }

}

package com.simpletak.takscheduler.api.exception;

import org.springframework.http.HttpStatus;

public class InvalidCronExpressionException extends WebException {

    public InvalidCronExpressionException() {
        super("Invalid CRON date", HttpStatus.BAD_REQUEST);
    }

}
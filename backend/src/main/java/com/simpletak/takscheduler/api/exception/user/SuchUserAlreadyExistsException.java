package com.simpletak.takscheduler.api.exception.user;

import com.simpletak.takscheduler.api.exception.WebException;
import org.springframework.http.HttpStatus;

public class SuchUserAlreadyExistsException extends WebException {
    public SuchUserAlreadyExistsException() {
        super("Such user already exists", HttpStatus.CONFLICT);
    }
}

package com.simpletak.takscheduler.exception.user;

import com.simpletak.takscheduler.exception.WebException;
import org.springframework.http.HttpStatus;

public class SuchUserAlreadyExistsException extends WebException {
    public SuchUserAlreadyExistsException() {
        super("Such user already exists", HttpStatus.CONFLICT);
    }
}

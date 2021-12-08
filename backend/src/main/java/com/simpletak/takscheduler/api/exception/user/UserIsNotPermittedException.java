package com.simpletak.takscheduler.api.exception.user;

import com.simpletak.takscheduler.api.exception.WebException;
import org.springframework.http.HttpStatus;

public class UserIsNotPermittedException extends WebException {
    public UserIsNotPermittedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}

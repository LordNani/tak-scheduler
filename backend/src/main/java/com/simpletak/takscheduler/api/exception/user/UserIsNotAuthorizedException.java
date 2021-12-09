package com.simpletak.takscheduler.api.exception.user;

import com.simpletak.takscheduler.api.exception.WebException;
import org.springframework.http.HttpStatus;

public class UserIsNotAuthorizedException extends WebException {
    public UserIsNotAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}

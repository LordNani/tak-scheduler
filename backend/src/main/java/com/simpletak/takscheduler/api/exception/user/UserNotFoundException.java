package com.simpletak.takscheduler.api.exception.user;

import com.simpletak.takscheduler.api.exception.WebException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends WebException {
    public UserNotFoundException() {
        super("User with this id not found", HttpStatus.NOT_FOUND);
    }
}

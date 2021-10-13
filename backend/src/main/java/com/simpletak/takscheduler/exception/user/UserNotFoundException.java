package com.simpletak.takscheduler.exception.user;

import com.simpletak.takscheduler.exception.WebException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends WebException {
    public UserNotFoundException() {
        super("User with this id not found", HttpStatus.NOT_FOUND);
    }
}

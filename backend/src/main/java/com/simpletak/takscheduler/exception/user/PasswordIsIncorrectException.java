package com.simpletak.takscheduler.exception.user;

import com.simpletak.takscheduler.exception.WebException;
import org.springframework.http.HttpStatus;

public class PasswordIsIncorrectException extends WebException {
    public PasswordIsIncorrectException() {
        super("Password is incorrect or such user doesn't exist", HttpStatus.FORBIDDEN);
    }
}

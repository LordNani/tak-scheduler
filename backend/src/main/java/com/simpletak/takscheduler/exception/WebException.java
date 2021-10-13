package com.simpletak.takscheduler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WebException extends RuntimeException {

    private final HttpStatus httpStatus;

    public WebException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}

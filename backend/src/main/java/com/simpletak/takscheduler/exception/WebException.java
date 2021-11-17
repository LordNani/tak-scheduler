package com.simpletak.takscheduler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity is not found")
public class WebException extends RuntimeException {

    private final HttpStatus httpStatus;

    public WebException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}

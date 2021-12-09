package com.simpletak.takscheduler.api.exception.event;

import com.simpletak.takscheduler.api.exception.WebException;
import org.springframework.http.HttpStatus;

public class IncorrectYearOrTimeRangeException extends WebException {
    public IncorrectYearOrTimeRangeException() {
        super("Year should be from 2021 to 2121, month should be from 1 to 12", HttpStatus.BAD_REQUEST);
    }
}

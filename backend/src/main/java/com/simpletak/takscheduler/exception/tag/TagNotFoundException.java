package com.simpletak.takscheduler.exception.tag;

import com.simpletak.takscheduler.exception.WebException;
import org.springframework.http.HttpStatus;

public class TagNotFoundException extends WebException {

    public TagNotFoundException() {
        super("Tag with this name not found", HttpStatus.NOT_FOUND);
    }

}

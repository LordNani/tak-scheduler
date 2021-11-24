package com.simpletak.takscheduler.api.exception.tag;

import com.simpletak.takscheduler.api.exception.WebException;
import org.springframework.http.HttpStatus;

public class TagAlreadyExistsException extends WebException {

    public TagAlreadyExistsException() {
        super("Tag with this name already exists", HttpStatus.CONFLICT);
    }

}

package com.simpletak.takscheduler.exception.eventgroup;

import com.simpletak.takscheduler.exception.WebException;
import org.springframework.http.HttpStatus;

public class SuchEventGroupAlreadyExists extends WebException {

    public SuchEventGroupAlreadyExists() {
        super("Such event group already exists", HttpStatus.NOT_FOUND);
    }

}
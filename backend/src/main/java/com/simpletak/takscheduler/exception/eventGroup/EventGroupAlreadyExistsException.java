package com.simpletak.takscheduler.exception.eventGroup;
import com.simpletak.takscheduler.exception.WebException;
import org.springframework.http.HttpStatus;
public class EventGroupAlreadyExistsException extends WebException{
    public EventGroupAlreadyExistsException() {
        super("EventGroup with this name already exists", HttpStatus.CONFLICT);
    }
}

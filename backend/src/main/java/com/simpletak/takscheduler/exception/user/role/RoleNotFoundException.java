package com.simpletak.takscheduler.exception.user.role;

import com.simpletak.takscheduler.exception.WebException;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends WebException {
    public RoleNotFoundException() {
        super("Role not found", HttpStatus.NOT_FOUND);
    }
}


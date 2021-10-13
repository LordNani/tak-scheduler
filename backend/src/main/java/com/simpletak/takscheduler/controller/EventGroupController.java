package com.simpletak.takscheduler.controller;

import com.simpletak.takscheduler.dto.eventGroup.EventGroupRequestDTO;
import com.simpletak.takscheduler.dto.eventGroup.EventGroupResponseDTO;
import com.simpletak.takscheduler.model.user.UserEntity;
import com.simpletak.takscheduler.service.eventgroup.EventGroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/eventgroups")
public class EventGroupController {
    EventGroupService eventGroupService;

    @PostMapping("/create-eventgroup")
    public EventGroupResponseDTO createEventGroup(@Valid @RequestBody EventGroupRequestDTO eventGroupDto) {
        UUID owner = null;
        return eventGroupService.createEventGroup(eventGroupDto, owner);
    }
}

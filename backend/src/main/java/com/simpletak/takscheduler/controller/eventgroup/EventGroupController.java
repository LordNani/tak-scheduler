package com.simpletak.takscheduler.controller.eventgroup;

import com.simpletak.takscheduler.service.eventgroup.EventGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event-groups")
public class EventGroupController {
    private final EventGroupService eventGroupService;
}

package com.simpletak.takscheduler.controller.eventgroup;

import com.simpletak.takscheduler.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.dto.eventGroup.NewEventGroupDTO;
import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.service.eventgroup.EventGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event-groups")
public class EventGroupController {
    private final EventGroupService eventGroupService;

    @GetMapping("/{id}")
    public EventGroupDTO getEventGroup(@PathVariable("id") UUID id) {
        return eventGroupService.findEventGroupById(id);
    }

    @PostMapping
    public EventGroupDTO createEventGroup(@RequestBody NewEventGroupDTO eventGroupDTO) {
        return eventGroupService.createEventGroup(eventGroupDTO);
    }

    @PutMapping
    public EventGroupDTO updateEventGroup(@RequestBody EventGroupDTO eventGroupDTO) {
        return eventGroupService.updateEventGroup(eventGroupDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEventGroup(@PathVariable("id") UUID id) {
        eventGroupService.deleteEventGroup(id);
    }
}
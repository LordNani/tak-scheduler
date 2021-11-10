package com.simpletak.takscheduler.controller.eventgroup;

import com.simpletak.takscheduler.dto.event.EventDTO;
import com.simpletak.takscheduler.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.model.event.EventEntity;
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
    public EventGroupEntity getEventGroup(@PathVariable("id") UUID id) {
        return eventGroupService.findEventGroupById(id);
    }

    @PostMapping
    public EventGroupEntity createEventGroup(@RequestBody EventGroupDTO eventGroupDTO) {
        return eventGroupService.createEventGroup(eventGroupDTO);
    }

    @PutMapping
    public EventGroupEntity updateEventGroup(@RequestBody EventGroupDTO eventGroupDTO) {
        return eventGroupService.updateEventGroup(eventGroupDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEventGroup(@PathVariable("id") UUID id) {
        eventGroupService.deleteEventGroup(id);
    }
}
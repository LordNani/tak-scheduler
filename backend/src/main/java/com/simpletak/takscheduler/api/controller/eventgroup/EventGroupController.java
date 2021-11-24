package com.simpletak.takscheduler.api.controller.eventgroup;

import com.simpletak.takscheduler.api.config.Response;
import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.api.dto.eventGroup.NewEventGroupDTO;
import com.simpletak.takscheduler.api.service.eventgroup.EventGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event-groups")
public class EventGroupController {
    private final EventGroupService eventGroupService;

    @GetMapping("/{id}")
    public Response<EventGroupDTO> getEventGroup(@PathVariable("id") UUID id) {
        return Response.success(eventGroupService.findEventGroupById(id));
    }

    @PostMapping
    public Response<EventGroupDTO> createEventGroup(@Valid @RequestBody NewEventGroupDTO eventGroupDTO) {
        return Response.success(eventGroupService.createEventGroup(eventGroupDTO));
    }

    @PutMapping
    public Response<EventGroupDTO> updateEventGroup(@Valid @RequestBody EventGroupDTO eventGroupDTO) {
        return Response.success(eventGroupService.updateEventGroup(eventGroupDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteEventGroup(@PathVariable("id") UUID id) {
        eventGroupService.deleteEventGroup(id);
    }
}
package com.simpletak.takscheduler.controller.event;

import com.simpletak.takscheduler.dto.event.EventDTO;
import com.simpletak.takscheduler.dto.event.NewEventDTO;
import com.simpletak.takscheduler.model.event.EventEntity;
import com.simpletak.takscheduler.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    @GetMapping("/{id}")
    public EventDTO getEvent(@PathVariable("id") UUID id) {
        return eventService.findEventById(id);
    }

    @PostMapping
    public EventDTO createEvent(@RequestBody NewEventDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    @PutMapping
    public EventDTO updateEvent(@RequestBody EventDTO eventDTO) {
        return eventService.updateEvent(eventDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable("id") UUID id) {
        eventService.deleteEvent(id);
    }
}
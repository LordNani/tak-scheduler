package com.simpletak.takscheduler.api.controller.event;

import com.simpletak.takscheduler.api.config.Response;
import com.simpletak.takscheduler.api.dto.event.EventDTO;
import com.simpletak.takscheduler.api.dto.event.NewEventDTO;
import com.simpletak.takscheduler.api.model.event.scheduling.EventSchedulingByCronDTO;
import com.simpletak.takscheduler.api.model.event.scheduling.EventSchedulingByDateDTO;
import com.simpletak.takscheduler.api.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    @GetMapping("/{id}")
    public Response<EventDTO> getEvent(@PathVariable("id") UUID id) {
        return Response.success(eventService.findEventById(id));
    }

    @PostMapping
    public Response<EventDTO> createEvent(@Valid @RequestBody NewEventDTO eventDTO) {
        return Response.success(eventService.createEvent(eventDTO));
    }

    @PutMapping
    public Response<EventDTO> updateEvent(@Valid @RequestBody EventDTO eventDTO) {
        return Response.success(eventService.updateEvent(eventDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable("id") UUID id) {
        eventService.deleteEvent(id);
    }

    @PostMapping("/schedule")
    public Response<String> scheduleEventV2(@Valid @RequestBody EventSchedulingByCronDTO eventDTO) {
        eventService.scheduleEventByCron(eventDTO);
        return Response.success("Event has been scheduled.");
    }

    @PostMapping("/schedule-by-date")
    public Response<String> scheduleEvent(@Valid @RequestBody EventSchedulingByDateDTO eventSchedulingByDateDTO) {
        System.out.println("eventEntity.getEventDate(): " + eventSchedulingByDateDTO.getExecutionDate());
        eventService.scheduleEventByDate(eventSchedulingByDateDTO);

        return Response.success("Event has been scheduled.");
    }

    @GetMapping("/by-month/{year}/{month}")
    public Response<List<EventDTO>> eventsInMonth(@PathVariable("year") Integer year, @PathVariable("month") Integer month){
        return Response.success(eventService.eventsInMonth(year, month));
    }

    @GetMapping("/by-day/{year}/{month}/{day}")
    public Response<List<EventDTO>> eventsWithinDay(@PathVariable("year") Integer year,
                                                  @PathVariable("month") Integer month,
                                                  @PathVariable("day") Integer day){
        return Response.success(eventService.eventsWithinDay(year, month, day));
    }
}
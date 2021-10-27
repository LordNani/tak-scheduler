package com.simpletak.takscheduler.service.event;

import com.simpletak.takscheduler.dto.event.EventDTO;
import com.simpletak.takscheduler.exception.event.EventNotFoundException;
import com.simpletak.takscheduler.model.event.EventEntity;
import com.simpletak.takscheduler.repository.event.EventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log
public class EventService {

    private final EventRepository eventRepository;

    public EventEntity findEventById(UUID id) {
        return eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
    }

    public EventEntity createEvent(EventDTO eventDTO) {
        EventEntity eventEntity = toEntity(eventDTO);
        return eventRepository.save(eventEntity);
    }

    public EventEntity updateEvent(EventDTO eventDTO) {
        EventEntity eventEntity = toEntity(eventDTO);
        return eventRepository.saveAndFlush(eventEntity);
    }

    public void deleteEvent(UUID id) {
        eventRepository.deleteById(id);
    }

    public EventEntity toEntity(EventDTO eventDTO) {
        return EventEntity.builder()
                .id(eventDTO.getId())
                .eventName(eventDTO.getEventName())
                .eventDescription(eventDTO.getEventDescription())
                .eventPriority(eventDTO.getEventPriority())
                .isReoccurring(eventDTO.isReoccurring())
                .eventFreq(eventDTO.getEventFreq())
                .eventDate(eventDTO.getEventDate())
                .eventTime(eventDTO.getEventTime())
                .build();
    }
}

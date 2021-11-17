package com.simpletak.takscheduler.service.event;

import com.simpletak.takscheduler.dto.event.EventDTO;
import com.simpletak.takscheduler.dto.event.EventMapper;
import com.simpletak.takscheduler.dto.event.NewEventDTO;
import com.simpletak.takscheduler.dto.eventGroup.EventGroupMapper;
import com.simpletak.takscheduler.exception.event.EventNotFoundException;
import com.simpletak.takscheduler.exception.eventgroup.EventGroupNotFoundException;
import com.simpletak.takscheduler.model.event.EventEntity;
import com.simpletak.takscheduler.repository.event.EventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper mapper;

    public EventDTO findEventById(UUID id) {
        return mapper.fromEntity(eventRepository.findById(id).orElseThrow(EventNotFoundException::new));
    }

    public EventDTO createEvent(NewEventDTO eventDTO) {
        EventEntity eventEntity = mapper.toEntity(new EventDTO(eventDTO, null));
        return mapper.fromEntity(eventRepository.saveAndFlush(eventEntity));
    }

    public EventDTO updateEvent(EventDTO eventDTO) {
        EventEntity eventEntity = mapper.toEntity(eventDTO);
        if(!eventRepository.existsById(eventDTO.getId())) throw new EventNotFoundException();
        return mapper.fromEntity(eventRepository.saveAndFlush(eventEntity));
    }

    public void deleteEvent(UUID id) {
        try {
            eventRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new EventGroupNotFoundException();
        }
    }
}

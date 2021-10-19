package com.simpletak.takscheduler.service.event;


import com.simpletak.takscheduler.config.logging.TakLayout;
import com.simpletak.takscheduler.model.event.EventEntity;
import com.simpletak.takscheduler.repository.event.EventRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.Charset;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public EventEntity findEventById(UUID id) {
        return eventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}

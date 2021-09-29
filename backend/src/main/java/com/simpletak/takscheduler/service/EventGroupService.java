package com.simpletak.takscheduler.service;

import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.repository.eventGroup.EventGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class EventGroupService {

    private EventGroupRepository eventGroupRepository;

    public EventGroupEntity findEventGroupById(UUID id) {
        return eventGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Autowired
    public void setEventGroupRepository(EventGroupRepository eventGroupRepository) {
        this.eventGroupRepository = eventGroupRepository;
    }
}

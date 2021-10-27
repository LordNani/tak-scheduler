package com.simpletak.takscheduler.service.eventgroup;

import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.repository.eventGroup.EventGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventGroupService {
    private EventGroupRepository eventGroupRepository;

    public EventGroupEntity findEventGroupById(UUID id) {
        return eventGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}

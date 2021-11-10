package com.simpletak.takscheduler.service.eventgroup;

import com.simpletak.takscheduler.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.model.user.UserEntity;
import com.simpletak.takscheduler.repository.event.EventRepository;
import com.simpletak.takscheduler.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventGroupService {
    private EventGroupRepository eventGroupRepository;
    private final UserRepository userRepository;

    public EventGroupEntity findEventGroupById(UUID id) {
        return eventGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public EventGroupEntity createEventGroup(EventGroupDTO eventGroupDTO){
        EventGroupEntity eventGroupEntity = toEntity(eventGroupDTO);
        return eventGroupRepository.saveAndFlush(eventGroupEntity);
    }

    public EventGroupEntity updateEventGroup(EventGroupDTO eventGroupDTO){
        EventGroupEntity eventGroupEntity = toEntity(eventGroupDTO);
        return eventGroupRepository.saveAndFlush(eventGroupEntity);
    }

    public void deleteEventGroup(UUID id){
        eventGroupRepository.deleteById(id);
    }

    public EventGroupEntity toEntity(EventGroupDTO eventGroupDTO) {
        UserEntity owner = userRepository.findById(eventGroupDTO.getOwnerId())
                .orElseThrow(EntityNotFoundException::new);
        return EventGroupEntity.builder()
                .id(eventGroupDTO.getId())
                .eventName(eventGroupDTO.getEventName())
                .eventGroupDescription(eventGroupDTO.getEventGroupDescription())
                .owner(owner)
                .build();
    }
}

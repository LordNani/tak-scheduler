package com.simpletak.takscheduler.api.dto.eventGroup;

import com.simpletak.takscheduler.api.dto.Mapper;
import com.simpletak.takscheduler.api.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventGroupMapper implements Mapper<EventGroupEntity, EventGroupDTO> {

    UserRepository userRepository;

    @Autowired
    EventGroupMapper(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public EventGroupEntity toEntity(EventGroupDTO dto) {
        UserEntity owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(UserNotFoundException::new);
        return EventGroupEntity.builder()
                .id(dto.getId())
                .eventName(dto.getEventName())
                .eventGroupDescription(dto.getEventGroupDescription())
                .owner(owner)
                .build();
    }

    @Override
    public EventGroupDTO fromEntity(EventGroupEntity entity) {
        return new EventGroupDTO(entity.getEventName(), entity.getEventGroupDescription(), entity.getOwner().getId(), entity.getId());
    }
}

package com.simpletak.takscheduler.dto.eventGroup;

import com.simpletak.takscheduler.dto.Mapper;
import com.simpletak.takscheduler.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.model.user.UserEntity;
import com.simpletak.takscheduler.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
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

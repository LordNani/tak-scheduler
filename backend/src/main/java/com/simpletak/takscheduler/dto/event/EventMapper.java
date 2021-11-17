package com.simpletak.takscheduler.dto.event;

import com.simpletak.takscheduler.dto.Mapper;
import com.simpletak.takscheduler.exception.eventgroup.EventGroupNotFoundException;
import com.simpletak.takscheduler.model.event.EventEntity;
import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventMapper implements Mapper<EventEntity, EventDTO> {
    EventGroupRepository eventGroupRepository;

    @Autowired
    EventMapper(EventGroupRepository eventGroupRepository){
        this.eventGroupRepository = eventGroupRepository;
    }

    @Override
    public EventEntity toEntity(EventDTO eventDTO) {
        EventGroupEntity eventGroup = eventGroupRepository.findById(
                eventDTO.getEventGroupId()).orElseThrow(EventGroupNotFoundException::new);

        return EventEntity.builder()
                .id(eventDTO.getId())
                .eventName(eventDTO.getEventName())
                .eventDescription(eventDTO.getEventDescription())
                .eventPriority(eventDTO.getEventPriority())
                .isReoccurring(eventDTO.isReoccurring())
                .eventFreq(eventDTO.getEventFreq())
                .eventDate(eventDTO.getEventDate())
                .eventTime(eventDTO.getEventTime())
                .eventGroup(eventGroup)
                .build();
    }

    @Override
    public EventDTO fromEntity(EventEntity entity) {
        return new EventDTO(entity.getId(), entity.getEventName(), entity.getEventDescription(), entity.getEventPriority(),
                entity.isReoccurring(), entity.getEventFreq(), entity.getEventDate(), entity.getEventTime(),
                entity.getEventGroup().getId());
    }
}

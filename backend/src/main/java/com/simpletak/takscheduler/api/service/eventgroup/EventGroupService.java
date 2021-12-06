package com.simpletak.takscheduler.api.service.eventgroup;

import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupMapper;
import com.simpletak.takscheduler.api.dto.eventGroup.NewEventGroupDTO;
import com.simpletak.takscheduler.api.exception.eventgroup.EventGroupNotFoundException;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.repository.eventGroup.EventGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventGroupService {
    private final EventGroupRepository eventGroupRepository;
    private final EventGroupMapper mapper;

    public EventGroupDTO findEventGroupById(UUID id) {
        return mapper.fromEntity(eventGroupRepository.findById(id).orElseThrow(EventGroupNotFoundException::new));
    }

    public EventGroupDTO createEventGroup(NewEventGroupDTO eventGroupDTO){
        EventGroupEntity eventGroupEntity = mapper.toEntity(new EventGroupDTO(eventGroupDTO, null));
        return mapper.fromEntity(eventGroupRepository.saveAndFlush(eventGroupEntity));
    }

    public EventGroupDTO updateEventGroup(EventGroupDTO eventGroupDTO){
        EventGroupEntity eventGroupEntity = mapper.toEntity(eventGroupDTO);
        if(!eventGroupRepository.existsById(eventGroupDTO.getId())) throw new EventGroupNotFoundException();
        return mapper.fromEntity(eventGroupRepository.saveAndFlush(eventGroupEntity));
    }

    public void deleteEventGroup(UUID id){
        try {
            eventGroupRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new EventGroupNotFoundException();
        }
    }

    public List<EventGroupDTO> getEventGroupsByTags(List<UUID> tags) {
        return List.of();
    }
}

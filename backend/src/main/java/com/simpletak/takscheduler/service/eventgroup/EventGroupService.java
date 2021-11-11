package com.simpletak.takscheduler.service.eventgroup;

import com.simpletak.takscheduler.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.dto.eventGroup.EventGroupMapper;
import com.simpletak.takscheduler.dto.eventGroup.NewEventGroupDTO;
import com.simpletak.takscheduler.exception.eventgroup.EventGroupNotFoundException;
import com.simpletak.takscheduler.exception.eventgroup.SuchEventGroupAlreadyExists;
import com.simpletak.takscheduler.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.model.user.UserEntity;
import com.simpletak.takscheduler.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventGroupService {
    private final EventGroupRepository eventGroupRepository;
    private final UserRepository userRepository;
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
}

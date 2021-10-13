package com.simpletak.takscheduler.service.eventgroup;

import com.simpletak.takscheduler.dto.eventGroup.EventGroupRequestDTO;
import com.simpletak.takscheduler.dto.eventGroup.EventGroupResponseDTO;
import com.simpletak.takscheduler.dto.tag.TagRequestDTO;
import com.simpletak.takscheduler.dto.tag.TagResponseDTO;
import com.simpletak.takscheduler.exception.tag.TagAlreadyExistsException;
import com.simpletak.takscheduler.exception.tag.TagNotFoundException;
import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.model.tag.TagEntity;
import com.simpletak.takscheduler.model.user.UserEntity;
import com.simpletak.takscheduler.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.repository.tag.TagRepository;
import com.simpletak.takscheduler.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class EventGroupService {

    private EventGroupRepository eventGroupRepository;
    @Autowired
    private UserService userService;
    @Autowired
    public void setEventGroupRepository(EventGroupRepository eventGroupRepository) {
        this.eventGroupRepository = eventGroupRepository;
    }


    public EventGroupResponseDTO createEventGroup(EventGroupRequestDTO eventGroupRequestDTO, UUID ownerId){
        if(eventGroupRepository.existsByName(eventGroupRequestDTO.getEventGroupName())) throw new TagAlreadyExistsException();

        UserEntity owner = userService.getUserById(ownerId);

        EventGroupEntity eventGroupEntity = EventGroupEntity.builder()
                .eventGroupName(eventGroupRequestDTO.getEventGroupName())
                .eventGroupDescription(eventGroupRequestDTO.getEventGroupDescription())
                .owner(owner)
                .build();

        eventGroupRepository.save(eventGroupEntity);

        return findEventGroup(eventGroupRequestDTO, owner.getId());
    }

    public EventGroupResponseDTO findEventGroup(EventGroupRequestDTO eventGroupRequestDTO, UUID ownerId){
        EventGroupEntity foundEventGroup = eventGroupRepository.findByEventGroupName(eventGroupRequestDTO.getEventGroupName());

        if(isNull(foundEventGroup)) throw new TagNotFoundException();

        EventGroupResponseDTO responseDTO = new EventGroupResponseDTO(foundEventGroup.getId(), foundEventGroup.getEventGroupName(),foundEventGroup.getEventGroupDescription(), ownerId);

        return responseDTO;
    }


    public EventGroupEntity findEventGroupById(UUID id) {
        return eventGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


}

package com.simpletak.takscheduler.api.service.eventgroup;

import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupMapper;
import com.simpletak.takscheduler.api.dto.eventGroup.NewEventGroupDTO;
import com.simpletak.takscheduler.api.exception.eventgroup.EventGroupNotFoundException;
import com.simpletak.takscheduler.api.exception.user.UserIsNotAuthorizedException;
import com.simpletak.takscheduler.api.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.repository.event.EventRepository;
import com.simpletak.takscheduler.api.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.api.repository.eventGroup.EventGroupRepositoryPagingAndSorting;
import com.simpletak.takscheduler.api.repository.subscription.SubscriptionRepository;
import com.simpletak.takscheduler.api.repository.tagEventGroup.TagEventGroupRepository;
import com.simpletak.takscheduler.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log
@RequiredArgsConstructor
public class EventGroupService {
    private final EventGroupRepository eventGroupRepository;
    private final EventGroupRepositoryPagingAndSorting eventGroupRepositoryPagingAndSorting;
    private final UserRepository userRepository;
    private final TagEventGroupRepository tagEventGroupRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final EventRepository eventRepository;
    private final EventGroupMapper mapper;


    public EventGroupDTO findEventGroupById(UUID id) {
        EventGroupEntity eventGroupEntity = eventGroupRepository.findById(id).orElseThrow(EventGroupNotFoundException::new);
        EventGroupDTO eventGroupDTO = mapper.fromEntity(eventGroupEntity);
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
        setSubscriptionAndOwnedToEventGroupDTO(eventGroupDTO, userId);

        return eventGroupDTO;
    }

    private void setSubscriptionAndOwnedToEventGroupDTO(EventGroupDTO eventGroupDTO,  UUID userId) {

        UUID eventGroupId = eventGroupDTO.getId();
        boolean subscribed = subscriptionRepository.existsByEventGroupEntity_IdAndUserEntity_Id(eventGroupId, userId);
        eventGroupDTO.setSubscribed(subscribed);

        boolean isOwned = eventGroupDTO.getOwnerId().equals(userId);
        eventGroupDTO.setOwned(isOwned);
    }

    public EventGroupDTO createEventGroup(NewEventGroupDTO eventGroupDTO, UUID userId) {
        EventGroupEntity eventGroupEntity = mapper.toEntity(new EventGroupDTO(eventGroupDTO, null, userId));
        EventGroupDTO savedDto =  mapper.fromEntity(eventGroupRepository.saveAndFlush(eventGroupEntity));

        setSubscriptionAndOwnedToEventGroupDTO(savedDto, userId);
        return savedDto;
    }

    public EventGroupDTO updateEventGroup(EventGroupDTO eventGroupDTO) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();

        if (!eventGroupRepository.existsById(eventGroupDTO.getId())) throw new EventGroupNotFoundException();
        EventGroupEntity eventGroupEntity = mapper.toEntity(eventGroupDTO);

        if (!userId.equals(eventGroupEntity.getOwner().getId())) {
            throw new UserIsNotAuthorizedException("You are not authorized to edit this event group.");
        }

        EventGroupDTO savedDto = mapper.fromEntity(eventGroupRepository.saveAndFlush(eventGroupEntity));
        setSubscriptionAndOwnedToEventGroupDTO(savedDto, userId);
        return savedDto;
    }

    @Transactional
    public void deleteEventGroup(UUID id) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();

        EventGroupEntity eventGroupEntity = eventGroupRepository
                .findById(id)
                .orElseThrow(EventGroupNotFoundException::new);

        if (!userId.equals(eventGroupEntity.getOwner().getId())) {
            throw new UserIsNotAuthorizedException("You are not authorized to delete this event group.");
        }
        subscriptionRepository.deleteByEventGroupEntity_Id(id);
        eventRepository.deleteEventEntitiesByEventGroup_Id(id);
        eventGroupRepository.deleteById(id);
    }

    public List<EventGroupDTO> getEventGroupsByTags(List<UUID> tags) {
        var eventGroups = tagEventGroupRepository.getEventGroupIdsByAllTagIds(
                tags.stream().map(UUID::toString).collect(Collectors.toList()));


        List<EventGroupDTO> eventGroupDTOs = eventGroupRepository.findAllById(eventGroups).stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
        for (EventGroupDTO eventGroupDTO : eventGroupDTOs) {
            setSubscriptionAndOwnedToEventGroupDTO(eventGroupDTO, userId);
        }

        return eventGroupDTOs;
    }

//    @Cacheable("eventGroups")
    public Page<EventGroupDTO> getEventGroupsByUser(int page, int size, UUID userId) {
        log.info("In getEventGroups");

        Pageable pageConfig = PageRequest.of(page, size);
        UserEntity user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Page<EventGroupEntity> eventGroupEntities =
                eventGroupRepositoryPagingAndSorting.findAllByOwner(user, pageConfig);

        Page<EventGroupDTO> eventGroupDTOs = eventGroupEntities.map(mapper::fromEntity);

        for (EventGroupDTO eventGroupDTO : eventGroupDTOs) {
            setSubscriptionAndOwnedToEventGroupDTO(eventGroupDTO, userId);
        }
        return eventGroupDTOs;
    }
}

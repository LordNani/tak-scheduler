package com.simpletak.takscheduler.api.service.eventgroup;

import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupMapper;
import com.simpletak.takscheduler.api.dto.eventGroup.NewEventGroupDTO;
import com.simpletak.takscheduler.api.exception.eventgroup.EventGroupNotFoundException;
import com.simpletak.takscheduler.api.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.api.repository.eventGroup.EventGroupRepositoryPagingAndSorting;
import com.simpletak.takscheduler.api.repository.tagEventGroup.TagEventGroupRepository;
import com.simpletak.takscheduler.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventGroupService {
    private final EventGroupRepository eventGroupRepository;
    private final EventGroupRepositoryPagingAndSorting eventGroupRepositoryPagingAndSorting;
    private final UserRepository userRepository;
    private final EventGroupMapper mapper;
    private final TagEventGroupRepository tagEventGroupRepository;
    private final CacheManager cacheManager;
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
        var eventGroups = tagEventGroupRepository.getEventGroupIdsByAllTagIds(
                tags.stream().map(UUID::toString).collect(Collectors.toList()));
        return eventGroupRepository.findAllById(eventGroups).stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Cacheable("eventGroups")
    public Page<EventGroupDTO> getEventGroupsByUser(UUID userId, int page, int size) {
        System.out.println("IN getEventGroups");
        Pageable pageConfig = PageRequest.of(page, size);
        UserEntity user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Page<EventGroupEntity> eventGroupEntities =
                eventGroupRepositoryPagingAndSorting.findAllByOwner(user, pageConfig);
        return eventGroupEntities.map(mapper::fromEntity);
    }
}

package com.simpletak.takscheduler.api.service.tageventgroup;

import com.simpletak.takscheduler.api.dto.tagEventGroup.TagEventGroupDTO;
import com.simpletak.takscheduler.api.dto.tagEventGroup.TagEventGroupMapper;
import com.simpletak.takscheduler.api.exception.eventgroup.EventGroupNotFoundException;
import com.simpletak.takscheduler.api.exception.tag.TagNotFoundException;
import com.simpletak.takscheduler.api.exception.user.UserIsNotAuthorizedException;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.model.tag.TagEntity;
import com.simpletak.takscheduler.api.model.tagEventGroup.TagEventGroupEntity;
import com.simpletak.takscheduler.api.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.api.repository.tag.TagRepository;
import com.simpletak.takscheduler.api.repository.tagEventGroup.TagEventGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TagEventGroupServiceImpl implements TagEventGroupService {
    private final TagEventGroupRepository tagEventGroupRepository;
    private final EventGroupRepository eventGroupRepository;
    private final TagRepository tagRepository;
    private final TagEventGroupMapper tagEventGroupMapper;

    @Override
    public TagEventGroupDTO addTagToEventGroup(TagEventGroupDTO tagEventGroupDTO) {
        EventGroupEntity eventGroupEntity = eventGroupRepository
                .findById(tagEventGroupDTO.getEventGroupID())
                .orElseThrow(EventGroupNotFoundException::new);
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
        boolean isOwned = eventGroupEntity.getOwner().getId().equals(userId);

        if (!isOwned) {
            throw new UserIsNotAuthorizedException("User is not authorized to add tag to this group.");
        }


        TagEntity tagEntity = tagRepository
                .findById(tagEventGroupDTO.getTagID())
                .orElseThrow(TagNotFoundException::new);

        TagEventGroupEntity tagEventGroupEntity =
                TagEventGroupEntity.builder()
                        .eventGroupEntity(eventGroupEntity)
                        .tag(tagEntity)
                        .build();
        TagEventGroupDTO tagEventGroupResDTO = tagEventGroupMapper.fromEntity(
                tagEventGroupRepository.save(tagEventGroupEntity)
        );

        return tagEventGroupResDTO;
    }

    @Override
    public void deleteEventGroupTag(UUID id) {
        TagEventGroupEntity tagEventGroupEntity =
                tagEventGroupRepository.findById(id)
                        .orElseThrow(TagNotFoundException::new);

        EventGroupEntity eventGroupEntity = tagEventGroupEntity.getEventGroupEntity();
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
        boolean isOwned = eventGroupEntity.getOwner().getId().equals(userId);

        if (!isOwned) {
            throw new UserIsNotAuthorizedException("User is not authorized to delete tag of this group.");
        }

        tagEventGroupRepository.deleteById(id);
    }


}


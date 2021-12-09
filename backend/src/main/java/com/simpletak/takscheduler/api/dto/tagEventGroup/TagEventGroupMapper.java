package com.simpletak.takscheduler.api.dto.tagEventGroup;

import com.simpletak.takscheduler.api.dto.Mapper;
import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.api.exception.eventgroup.EventGroupNotFoundException;
import com.simpletak.takscheduler.api.exception.tag.TagNotFoundException;
import com.simpletak.takscheduler.api.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.model.tag.TagEntity;
import com.simpletak.takscheduler.api.model.tagEventGroup.TagEventGroupEntity;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.api.repository.tag.TagRepository;
import com.simpletak.takscheduler.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagEventGroupMapper implements Mapper<TagEventGroupEntity, TagEventGroupDTO> {

    private final EventGroupRepository eventGroupRepository;
    private final TagRepository tagRepository;

    @Override
    public TagEventGroupEntity toEntity(TagEventGroupDTO dto) {
        TagEntity tagEntity = tagRepository
                .findById(dto.getTagID())
                .orElseThrow(TagNotFoundException::new);

        EventGroupEntity eventGroupEntity = eventGroupRepository
                .findById(dto.getEventGroupID())
                .orElseThrow(EventGroupNotFoundException::new);

        return TagEventGroupEntity.builder()
                .id(dto.getId())
                .tag(tagEntity)
                .eventGroupEntity(eventGroupEntity)
                .build();
    }

    @Override
    public TagEventGroupDTO fromEntity(TagEventGroupEntity entity) {
        return TagEventGroupDTO.builder()
                .id(entity.getId())
                .eventGroupID(entity.getEventGroupEntity().getId())
                .tagID(entity.getTag().getId())
                .build();
    }
}

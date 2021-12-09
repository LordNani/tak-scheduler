package com.simpletak.takscheduler.api.service.tageventgroup;

import com.simpletak.takscheduler.api.dto.tagEventGroup.TagEventGroupDTO;

import java.util.UUID;


public interface TagEventGroupService {
    TagEventGroupDTO addTagToEventGroup(TagEventGroupDTO tagEventGroupDTO);

    void deleteEventGroupTag(UUID id);
}

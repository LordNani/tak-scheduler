package com.simpletak.takscheduler.api.controller.eventgroup;

import com.simpletak.takscheduler.api.config.Response;
import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.api.dto.eventGroup.NewEventGroupDTO;
import com.simpletak.takscheduler.api.dto.tagEventGroup.TagEventGroupDTO;
import com.simpletak.takscheduler.api.service.eventgroup.EventGroupService;
import com.simpletak.takscheduler.api.service.tageventgroup.TagEventGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event-groups-tags")
public class EventGroupToTagController {
    private final TagEventGroupService eventGroupTagService;

    @PostMapping
    public Response<TagEventGroupDTO> createEventGroup(
            @Valid @RequestBody TagEventGroupDTO tagEventGroupDTO
    ) {
        return Response.success(eventGroupTagService
                .addTagToEventGroup(tagEventGroupDTO)
        );
    }

    @DeleteMapping("/{id}")
    public void deleteEventGroup(@PathVariable("id") UUID id) {
        eventGroupTagService.deleteEventGroupTag(id);
    }

}

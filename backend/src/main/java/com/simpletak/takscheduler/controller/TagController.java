package com.simpletak.takscheduler.controller;

import com.simpletak.takscheduler.dto.tag.TagRequestDTO;
import com.simpletak.takscheduler.dto.tag.TagResponseDTO;
import com.simpletak.takscheduler.service.tag.TagService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api/tags")
public class TagController {

    TagService tagService;

    @PostMapping("/create-tag")
    public TagResponseDTO createTag(@Valid @RequestBody TagRequestDTO tagDto) {
        return tagService.createTag(tagDto);
    }

    @PostMapping("/find-by-name")
    public TagResponseDTO getTagByName(@Valid @RequestBody TagRequestDTO tagDto) {
        return tagService.findTag(tagDto);
    }

}

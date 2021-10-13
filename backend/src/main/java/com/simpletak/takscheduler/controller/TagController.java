package com.simpletak.takscheduler.controller;

import com.simpletak.takscheduler.dto.tag.TagRequestDTO;
import com.simpletak.takscheduler.dto.tag.TagResponseDTO;
import com.simpletak.takscheduler.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {


    private final TagService tagService;

    @PostMapping("/create-tag")
    public TagResponseDTO createTag(@Valid @RequestBody TagRequestDTO tagDto) {
        return tagService.createTag(tagDto);
    }

    @GetMapping("/find-by-name")
    public TagResponseDTO getTagByName(@RequestParam(name = "tagName") String tagName) {
        return tagService.findTag(tagName);
    }

}

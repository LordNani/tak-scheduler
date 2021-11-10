package com.simpletak.takscheduler.controller;

import com.simpletak.takscheduler.config.Response;
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

    @PostMapping
    public Response<TagResponseDTO> createTag(@Valid @RequestBody TagRequestDTO tagDto) {

        return Response.success(tagService.createTag(tagDto));
    }

    @PutMapping
    public Response<TagResponseDTO> updateTag(@Valid @RequestBody TagRequestDTO tagDto) {
        return Response.success(tagService.updateTag(tagDto));
    }

    @DeleteMapping
    public void deleteTag(@Valid @RequestBody TagRequestDTO tagDto) {
        tagService.deleteTag(tagDto);
    }

    @GetMapping("/by-name")
    public Response<TagResponseDTO> getTagByName(@RequestParam(name = "tagName") String tagName) {
        return Response.success(tagService.findTagByName(tagName));
    }

    @GetMapping("/by-id")
    public Response<TagResponseDTO> getTagById(@RequestParam(name = "tagId") String tagId) {
        return Response.success(tagService.findTagById(tagId));
    }

}

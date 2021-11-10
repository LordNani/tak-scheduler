package com.simpletak.takscheduler.controller;

import com.simpletak.takscheduler.config.Response;
import com.simpletak.takscheduler.dto.tag.TagRequestDTO;
import com.simpletak.takscheduler.dto.tag.TagResponseDTO;
import com.simpletak.takscheduler.model.tag.TagEntity;
import com.simpletak.takscheduler.service.tag.TagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {


    private final TagService tagService;

    @Operation(summary = "Create new tag")
    @PostMapping
    public Response<TagResponseDTO> createTag(@Valid @RequestBody TagRequestDTO tagDto) {

        return Response.success(tagService.createTag(tagDto));
    }

    @Operation(summary = "Update existing tag")
    @PutMapping
    public Response<TagResponseDTO> updateTag(@Valid @RequestBody TagRequestDTO tagDto) {
        return Response.success(tagService.updateTag(tagDto));
    }

    @Operation(summary = "Delete a tag by id or name")
    @DeleteMapping
    public void deleteTag(@Valid @RequestBody TagRequestDTO tagDto) {
        tagService.deleteTag(tagDto);
    }

    @Operation(summary = "Get tag by name")
    @GetMapping("/by-name")
    public Response<TagResponseDTO> getTagByName(@RequestParam(name = "tagName") String tagName) {
        return Response.success(tagService.findTagByName(tagName));
    }

    @Operation(summary = "Get tag by id")
    @GetMapping("/by-id")
    public Response<TagResponseDTO> getTagById(@RequestParam(name = "tagId") String tagId) {
        return Response.success(tagService.findTagById(tagId));
    }

    @Operation(summary = "Get all tags, sorted by name")
    @GetMapping
    public Response<Page<TagEntity>> getTags(@RequestParam(name = "size") int size, @RequestParam(name = "page") int page) {
        return Response.success(tagService.findAll(size, page));
    }
}

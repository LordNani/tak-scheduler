package com.simpletak.takscheduler.api.controller.tag;

import com.simpletak.takscheduler.api.config.Response;
import com.simpletak.takscheduler.api.dto.tag.TagRequestDTO;
import com.simpletak.takscheduler.api.dto.tag.TagResponseDTO;
import com.simpletak.takscheduler.api.model.tag.TagEntity;
import com.simpletak.takscheduler.api.service.tag.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {


    private final TagService tagService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tag",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Tag with this name exists",
                    content = @Content)
    })
    @Operation(summary = "Create new tag")
    @PostMapping
    public Response<TagResponseDTO> createTag(@Valid @RequestBody TagRequestDTO tagDto) {

        return Response.success(tagService.createTag(tagDto));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the tag",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Tag not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Data conflicting",
                    content = @Content)
    })
    @Operation(summary = "Update existing tag")
    @PutMapping(path =  "/{id}")
    public Response<TagResponseDTO> updateTag(@PathVariable("id") UUID id, @Valid @RequestBody TagRequestDTO tagDto) {
        return Response.success(tagService.updateTag(id, tagDto));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Removed the tag",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Tag not found",
                    content = @Content)
    })
    @Operation(summary = "Delete a tag by id or name")
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") UUID id) {
        tagService.deleteById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tag",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Tag not found",
                    content = @Content)
    })
    @Operation(summary = "Get tag by name")
    @GetMapping("/by-name/{tagName}")
    public Response<TagResponseDTO> getTagByName(@PathVariable("tagName") String tagName) {
        return Response.success(tagService.findTagByName(tagName));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tag",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Tag not found",
                    content = @Content),
    })
    @Operation(summary = "Get tag by id")
    @GetMapping("/{id}")
    public Response<TagResponseDTO> getTagById(@PathVariable("id") UUID id) {
        return Response.success(tagService.findTagById(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of the tags, can be empty",
                    content = @Content(mediaType = "application/json")),
    })
    @Operation(summary = "Get all tags, sorted by name")
    @GetMapping
    public Response<Page<TagEntity>> getTags(@RequestParam(name = "size") int size, @RequestParam(name = "page") int page) {
        return Response.success(tagService.findAll(size, page));
    }
}

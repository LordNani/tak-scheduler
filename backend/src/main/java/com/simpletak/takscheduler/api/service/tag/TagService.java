package com.simpletak.takscheduler.api.service.tag;

import com.simpletak.takscheduler.api.dto.tag.TagRequestDTO;
import com.simpletak.takscheduler.api.dto.tag.TagResponseDTO;
import com.simpletak.takscheduler.api.exception.tag.TagAlreadyExistsException;
import com.simpletak.takscheduler.api.exception.tag.TagNotFoundException;
import com.simpletak.takscheduler.api.model.tag.TagEntity;
import com.simpletak.takscheduler.api.repository.tag.TagRepository;
import com.simpletak.takscheduler.api.repository.tagEventGroup.TagEventGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagEventGroupRepository tagEventGroupRepository;

    @Autowired
    public TagService(TagRepository tagRepository, TagEventGroupRepository tagEventGroupRepository){
        this.tagRepository = tagRepository;
        this.tagEventGroupRepository = tagEventGroupRepository;
    }

    public TagResponseDTO createTag(TagRequestDTO tagRequestDTO){
        if(tagRepository.existsByTagName(tagRequestDTO.getTagName())) throw new TagAlreadyExistsException();

        TagEntity tagEntity = toEntity(tagRequestDTO);

        tagRepository.saveAndFlush(tagEntity);

        return findTagByName(tagRequestDTO.getTagName());
    }

    public TagResponseDTO updateTag(UUID id, TagRequestDTO tagRequestDTO){
        if(!tagRepository.existsById(id))
            throw new TagNotFoundException();

        TagEntity tagEntity = toEntity(tagRequestDTO);
        tagEntity.setId(id);

        tagRepository.saveAndFlush(tagEntity);

        return findTagByName(tagRequestDTO.getTagName());
    }

    public TagResponseDTO findTagByName(String tagName){
        TagEntity foundTag = tagRepository.findByTagName(tagName);

        if(isNull(foundTag)) throw new TagNotFoundException();

        return new TagResponseDTO(foundTag.getId(), foundTag.getTagName());
    }

    public void deleteById(UUID tagId){
        if(!tagRepository.existsById(tagId)) throw new TagNotFoundException();
        tagEventGroupRepository.deleteAllByTag_Id(tagId);
        tagRepository.deleteById(tagId);
    }

    public TagResponseDTO findTagById(UUID tagId){
        TagEntity foundTag = tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);

        return new TagResponseDTO(foundTag.getId(), foundTag.getTagName());
    }


    public TagEntity toEntity(TagRequestDTO tagRequestDTO) {
        return TagEntity.builder()
                .tagName(tagRequestDTO.getTagName())
                .build();
    }

    public Page<TagEntity> findAll(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TagEntity> productPage =
                tagRepository.findAll(pageable);
        return productPage;
    }

    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }
}

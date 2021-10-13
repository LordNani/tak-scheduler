package com.simpletak.takscheduler.service.tag;

import com.simpletak.takscheduler.dto.tag.TagRequestDTO;
import com.simpletak.takscheduler.dto.tag.TagResponseDTO;
import com.simpletak.takscheduler.exception.tag.TagAlreadyExistsException;
import com.simpletak.takscheduler.exception.tag.TagNotFoundException;
import com.simpletak.takscheduler.model.tag.TagEntity;
import com.simpletak.takscheduler.repository.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public TagResponseDTO createTag(TagRequestDTO tagRequestDTO){
        if(tagRepository.existsByTagName(tagRequestDTO.getTagName())) throw new TagAlreadyExistsException();

        TagEntity tagEntity = TagEntity.builder()
                .tagName(tagRequestDTO.getTagName())
                .build();

        tagRepository.save(tagEntity);

        return findTag(tagRequestDTO.getTagName());
    }

    public TagResponseDTO findTag(String tagName){
        TagEntity foundTag = tagRepository.findByTagName(tagName);

        if(isNull(foundTag)) throw new TagNotFoundException();

        TagResponseDTO responseDTO = new TagResponseDTO(foundTag.getId(), foundTag.getTagName());

        return responseDTO;
    }


}

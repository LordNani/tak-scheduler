package com.simpletak.takscheduler.service.tag;

import com.simpletak.takscheduler.dto.tag.TagRequestDTO;
import com.simpletak.takscheduler.dto.tag.TagResponseDTO;
import com.simpletak.takscheduler.exception.tag.TagAlreadyExistsException;
import com.simpletak.takscheduler.exception.tag.TagNotFoundException;
import com.simpletak.takscheduler.model.tag.TagEntity;
import com.simpletak.takscheduler.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.repository.tag.TagRepository;
import com.simpletak.takscheduler.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public TagResponseDTO createTag(TagRequestDTO tagRequestDTO){
        if(tagRepository.existsByName(tagRequestDTO.getTagName())) throw new TagAlreadyExistsException();

        TagEntity tagEntity = TagEntity.builder()
                .tagName(tagRequestDTO.getTagName())
                .build();

        tagRepository.save(tagEntity);

        return findTag(tagRequestDTO);
    }

    public TagResponseDTO findTag(TagRequestDTO tagRequestDTO){
        TagEntity foundTag = tagRepository.findByTagName(tagRequestDTO.getTagName());

        if(isNull(foundTag)) throw new TagNotFoundException();

        TagResponseDTO responseDTO = new TagResponseDTO(foundTag.getId(), foundTag.getTagName());

        return responseDTO;
    }


}

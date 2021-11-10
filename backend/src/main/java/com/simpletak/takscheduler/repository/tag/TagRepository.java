package com.simpletak.takscheduler.repository.tag;

import com.simpletak.takscheduler.model.tag.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, UUID> {
    Optional<TagEntity> findById(UUID tagId);
    TagEntity findByTagName(String tagName);

    void deleteByTagNameOrId(String tagName, UUID tagId);
    boolean existsByTagName(String tagName);
    boolean existsByTagNameAndId(String tagName, UUID tagId);

    Page<TagEntity> findAll(Pageable pageable);


}

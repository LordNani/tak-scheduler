package com.simpletak.takscheduler.api.repository.tagEventGroup;

import com.simpletak.takscheduler.api.model.tagEventGroup.TagEventGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagEventGroupRepository extends JpaRepository<TagEventGroupEntity, UUID> {
}

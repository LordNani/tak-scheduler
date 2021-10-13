package com.simpletak.takscheduler.repository.eventGroup;

import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.model.tag.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventGroupRepository extends JpaRepository<EventGroupEntity, UUID> {
    boolean existsByName(String eventGroupName);
    Optional<EventGroupEntity> findById(UUID eventGroupId);
    EventGroupEntity findByEventGroupName(String eventGroupName);

    void deleteById(UUID eventGroupId);
}

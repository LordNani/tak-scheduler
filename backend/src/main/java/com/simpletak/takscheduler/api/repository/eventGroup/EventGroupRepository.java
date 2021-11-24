package com.simpletak.takscheduler.api.repository.eventGroup;

import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventGroupRepository extends JpaRepository<EventGroupEntity, UUID> {

}

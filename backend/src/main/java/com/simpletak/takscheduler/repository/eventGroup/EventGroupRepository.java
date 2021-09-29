package com.simpletak.takscheduler.repository.eventGroup;

import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventGroupRepository extends JpaRepository<EventGroupEntity, UUID> {

}

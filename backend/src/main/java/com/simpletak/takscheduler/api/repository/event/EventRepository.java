package com.simpletak.takscheduler.api.repository.event;

import com.simpletak.takscheduler.api.model.event.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {

    List<EventEntity> findAllByEventCronIsNotNull();
}

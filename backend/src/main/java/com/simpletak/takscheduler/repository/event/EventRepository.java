package com.simpletak.takscheduler.repository.event;

import com.simpletak.takscheduler.model.event.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {

}

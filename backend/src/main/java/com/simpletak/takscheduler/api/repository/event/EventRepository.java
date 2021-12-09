package com.simpletak.takscheduler.api.repository.event;

import com.simpletak.takscheduler.api.model.event.EventEntity;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {

    List<EventEntity> findAllByEventCronIsNotNull();

    List<EventEntity> findAllByStartEventDateLessThanEqualAndEndEventDateGreaterThan(Date rangeStart, Date rangeEnd);
}

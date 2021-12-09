package com.simpletak.takscheduler.api.service.event.scheduling;

import com.simpletak.takscheduler.api.model.event.EventEntity;
import com.simpletak.takscheduler.api.service.event.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EventRunnableTask implements Runnable {
    private EventEntity eventEntity;
    private final EventService eventService;

    @Override
    public void run() {
        eventService.notifyUser(eventEntity);

        System.out.println("Event with id " +  eventEntity.getId() +  " is running. Time: " + new Date());
    }

    public void setEventEntity(EventEntity eventEntity) {
        this.eventEntity = eventEntity;
    }
}

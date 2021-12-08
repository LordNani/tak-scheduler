package com.simpletak.takscheduler.api.init;

import com.simpletak.takscheduler.api.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitEventScheduling implements InitializingBean {
    private final EventService eventService;

    @Override
    public void afterPropertiesSet() {
        eventService.scheduleEvents();
    }
}

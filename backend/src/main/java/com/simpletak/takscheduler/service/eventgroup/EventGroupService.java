package com.simpletak.takscheduler.service.eventgroup;

import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.repository.eventGroup.EventGroupRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class EventGroupService {
private EventGroupRepository eventGroupRepository;
    private static final Marker ADMIN_USER = MarkerManager.getMarker("ADMIN");
    public EventGroupEntity findEventGroupById(UUID id) {
        Logger logger = LogManager.getLogger(getClass());
        logger.info("findEventGroupById: {}", id);
        logger.error(ADMIN_USER, "admin: findEventGroupById: {}", id);
        logger.error("not admin: findEventGroupById: {}", id);
        return eventGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Autowired
    public void setEventGroupRepository(EventGroupRepository eventGroupRepository) {
        this.eventGroupRepository = eventGroupRepository;
    }
}

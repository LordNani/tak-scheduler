package com.simpletak.takscheduler.api.config;


import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupDTO;
import com.simpletak.takscheduler.api.dto.eventGroup.EventGroupMapper;
import com.simpletak.takscheduler.api.dto.eventGroup.NewEventGroupDTO;
import com.simpletak.takscheduler.api.dto.user.role.RoleDTO;
import com.simpletak.takscheduler.api.model.event.EventEntity;
import com.simpletak.takscheduler.api.model.event.EventFreq;
import com.simpletak.takscheduler.api.model.event.EventPriority;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.repository.event.EventRepository;
import com.simpletak.takscheduler.api.service.event.EventService;
import com.simpletak.takscheduler.api.service.eventgroup.EventGroupService;
import com.simpletak.takscheduler.api.service.user.UserService;
import com.simpletak.takscheduler.api.service.user.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ApplicationListenerInitialize implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleService roleService;
    private final UserService userService;
    private final EventGroupService eventGroupService;
    private final EventService eventService;
    private final EventRepository eventRepository;

    private final EventGroupMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationListenerInitialize.class);


    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<RoleDTO> roles = roleService.getAllRoles();
        Optional<RoleDTO> userRole = roles.stream()
                .filter(role -> Objects.equals(role.getRole(), "ROLE_USER"))
                .findAny();
        Optional<RoleDTO> adminRole = roles.stream()
                .filter(role -> Objects.equals(role.getRole(), "ROLE_ADMIN"))
                .findAny();
        if(userRole.isEmpty()) roleService.createRole(new RoleDTO(null, "ROLE_USER"));
        if(adminRole.isEmpty()) roleService.createRole(new RoleDTO(null, "ROLE_ADMIN"));

        if(Boolean.FALSE.equals(userService.adminExists())){
            UserEntity admin = userService.createAdminEntity();
            logger.info(String.format("Created admin with credentials: \nusername: %s\npassword: %s",
                    admin.getUsername(), admin.getPassword()));

            List<EventGroupDTO> eventGroupDTOS = createTestEventGroups(admin);
            createTestEvents(eventGroupDTOS);


//            eventService.scheduleEvents(); // TODO remove when PROD
        }

        logger.info("Checked existence of admin, user roles");
    }

    private List<EventGroupDTO> createTestEventGroups(UserEntity admin) {
        List<EventGroupDTO> eventGroupDTOS = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            eventGroupDTOS.add(eventGroupService.createEventGroup(NewEventGroupDTO.builder()
                    .eventGroupName("nameGroup" + i)
                    .eventGroupDescription("descr" + i)
                    .build(),
                    admin.getId()
            ));
        }
        return eventGroupDTOS;
    }

    private void createTestEvents(List<EventGroupDTO> eventGroupDTOS) {
        for (EventGroupDTO eventGroupDTO : eventGroupDTOS) {
            EventGroupEntity e = mapper.toEntity(eventGroupDTO);

            for (int i = 0; i < 5; i++) {
                GregorianCalendar now = new GregorianCalendar();
                GregorianCalendar start = (GregorianCalendar) now.clone();
                start.add(Calendar.DAY_OF_MONTH, -2+i);
                GregorianCalendar end = (GregorianCalendar) now.clone();
                end.add(Calendar.DAY_OF_MONTH, 2+i);
                EventEntity eventEntity = EventEntity.builder()
                        .eventName("| nameEvent" + i + eventGroupDTO.getEventName())
                        .eventGroup(e)
                        .eventDescription("name" + i)
                        .nextEventDate(now.getTime())
                        .startEventDate(start.getTime())
                        .endEventDate(end.getTime())
                        .eventPriority(EventPriority.HIGH)
                        .eventFreq(EventFreq.DAILY)
                        .build();

                if (i == 1) eventEntity.setEventCron("20 * * * * ?");
                eventRepository.save(eventEntity);
            }
        }

    }
}

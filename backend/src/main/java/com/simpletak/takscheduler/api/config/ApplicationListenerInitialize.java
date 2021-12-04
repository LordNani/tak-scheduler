package com.simpletak.takscheduler.api.config;


import com.simpletak.takscheduler.api.dto.eventGroup.NewEventGroupDTO;
import com.simpletak.takscheduler.api.dto.user.role.RoleDTO;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.service.eventgroup.EventGroupService;
import com.simpletak.takscheduler.api.service.user.UserService;
import com.simpletak.takscheduler.api.service.user.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicationListenerInitialize implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleService roleService;
    private final UserService userService;
    private final EventGroupService eventGroupService;
    private final PasswordEncoder passwordEncoder;

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

            for (int i = 0; i < 15; i++) {
                eventGroupService.createEventGroup(NewEventGroupDTO.builder()
                        .eventName("name" + i)
                        .eventGroupDescription("descr" + i)
                        .ownerId(admin.getId())
                        .build()
                );
            }

        }

        logger.info("Checked existence of admin, user roles");
    }
}

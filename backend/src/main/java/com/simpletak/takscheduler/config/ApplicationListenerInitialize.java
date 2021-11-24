package com.simpletak.takscheduler.config;


import com.simpletak.takscheduler.dto.user.role.RoleDTO;
import com.simpletak.takscheduler.service.user.UserService;
import com.simpletak.takscheduler.service.user.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ApplicationListenerInitialize implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationListenerInitialize.class);

    @Autowired
    public ApplicationListenerInitialize(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


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

        if(!userService.adminExists()){
            var admin = userService.createAdmin();
            logger.info(String.format("Created admin with credentials: \nusername: %s\npassword: %s",
                    admin.getUsername(), admin.getPassword()));
        }

        logger.info("Checked existence of admin, user roles");
    }
}

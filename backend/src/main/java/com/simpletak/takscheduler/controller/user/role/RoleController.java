package com.simpletak.takscheduler.controller.user.role;


import com.simpletak.takscheduler.config.Response;
import com.simpletak.takscheduler.dto.event.EventDTO;
import com.simpletak.takscheduler.dto.event.NewEventDTO;
import com.simpletak.takscheduler.dto.user.role.RoleDTO;
import com.simpletak.takscheduler.service.user.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/roles")
@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/{id}")
    public Response<RoleDTO> getRole(@PathVariable("id") UUID id) {
        return Response.success(roleService.findRoleById(id));
    }

    @PostMapping
    public Response<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        return Response.success(roleService.createRole(roleDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable("id") UUID id) {
        roleService.deleteRole(id);
    }
}

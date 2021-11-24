package com.simpletak.takscheduler.api.controller.user.role;


import com.simpletak.takscheduler.api.config.Response;
import com.simpletak.takscheduler.api.dto.user.role.RoleDTO;
import com.simpletak.takscheduler.api.service.user.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

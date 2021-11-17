package com.simpletak.takscheduler.dto.user.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private UUID id;
    @NotNull
    private String role;
}

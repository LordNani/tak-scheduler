package com.simpletak.takscheduler.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserRequestDTO {
    @NotNull
    private UUID userId;
    @NotNull
    @Size(min = 8, max = 128)
    private String password;
}

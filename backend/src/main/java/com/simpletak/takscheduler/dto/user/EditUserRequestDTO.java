package com.simpletak.takscheduler.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequestDTO {
    private UUID userId;
    @NotNull
    @Size(min = 6, max = 64)
    private String username;
    @NotNull
    @Size(max = 128)
    private String fullName;
}

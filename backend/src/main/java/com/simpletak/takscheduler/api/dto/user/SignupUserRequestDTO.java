package com.simpletak.takscheduler.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupUserRequestDTO {
    @NotNull
    private String fullName;
    @NotNull
    @Size(min = 6, max = 64)
    private String username;
    @NotNull
    @Size(min = 8, max = 128)
    private String password;
}

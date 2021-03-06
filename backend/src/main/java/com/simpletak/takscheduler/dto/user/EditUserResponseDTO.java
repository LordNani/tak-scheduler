package com.simpletak.takscheduler.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserResponseDTO {
    private String username;
    private String fullName;
}

package com.simpletak.takscheduler.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupUserResponseDTO {
    private AuthTokenDTO tokenDTO;
    private UserInfoResponseDTO userInfoResponseDTO;

}

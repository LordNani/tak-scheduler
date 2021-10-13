package com.simpletak.takscheduler.controller;

import com.simpletak.takscheduler.dto.user.*;
import com.simpletak.takscheduler.service.user.SigninUserRequestDTO;
import com.simpletak.takscheduler.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("api/users")
public class UserController {
    UserService userService;

    @PostMapping("/signup")
    public SignupUserResponseDTO signup(@Valid @RequestBody SignupUserRequestDTO signupUserRequestDTO){
        return userService.registerUser(signupUserRequestDTO);
    }

    @PostMapping("/signin")
    public AuthTokenDTO signin(@Valid @RequestBody SigninUserRequestDTO signinUserRequestDTO){
        return userService.signinUser(signinUserRequestDTO);
    }

    @PostMapping("/edit")
    public EditUserResponseDTO editUser(@Valid @RequestBody EditUserRequestDTO editUserRequestDTO){
        return userService.editUser(editUserRequestDTO);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@Valid @RequestBody DeleteUserRequestDTO deleteUserRequestDTO){
        userService.deleteUser(deleteUserRequestDTO);
    }

    @GetMapping("/get-user")
    public UserInfoResponseDTO getUserInfo(@Valid @RequestParam UserInfoRequestDTO userInfoRequestDTO){
        return userService.getUser(userInfoRequestDTO.getId());
    }
}

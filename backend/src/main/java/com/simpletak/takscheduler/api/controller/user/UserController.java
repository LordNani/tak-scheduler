package com.simpletak.takscheduler.api.controller.user;

import com.simpletak.takscheduler.api.config.Response;
import com.simpletak.takscheduler.api.dto.user.*;
import com.simpletak.takscheduler.api.service.user.SigninUserRequestDTO;
import com.simpletak.takscheduler.api.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New user registered",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Such user already exists",
                    content = @Content)
    })
    @Operation(summary = "Register new user")
    @PostMapping("/sign-up")
    public Response<SignupUserResponseDTO> signup(@Valid @RequestBody SignupUserRequestDTO signupUserRequestDTO){
        return Response.success(userService.registerUser(signupUserRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign in operation was succesfull",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Password or login is incorrect",
                    content = @Content)
    })
    @Operation(summary = "Sign in for existing user", description = "This operation should return JWT token to use in every other request, but this functionality is not implemented yet")
    @PostMapping("/sign-in")
    public Response<AuthTokenDTO> signin(@Valid @RequestBody SigninUserRequestDTO signinUserRequestDTO){
        return Response.success(userService.signinUser(signinUserRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's personal info edited",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User with such id was not found",
                    content = @Content)
    })
    @Operation(summary = "User's personal info edit")
    @PreAuthorize("permitAll()")
    @PostMapping
    public Response<EditUserResponseDTO> editUser(@Valid @RequestBody EditUserRequestDTO editUserRequestDTO){
        return Response.success(userService.editUser(editUserRequestDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's account was deleted",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User with such id was not found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Password or login is incorrect",
                    content = @Content)
    })
    @Operation(summary = "Delete user (only he can do it, because password is required)")
    @DeleteMapping
    public void deleteUser(@Valid @RequestBody DeleteUserRequestDTO deleteUserRequestDTO){
        userService.deleteUser(deleteUserRequestDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's personal info received",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User with such id was not found",
                    content = @Content)
    })
    @Operation(summary = "Receive user's info by id")
    @GetMapping
    public Response<UserInfoResponseDTO> getUserInfo(@Valid @RequestParam UUID userId){
        return Response.success(userService.getUser(userId));
    }
}

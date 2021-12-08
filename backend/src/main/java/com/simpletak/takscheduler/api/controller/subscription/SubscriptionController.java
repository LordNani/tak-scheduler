package com.simpletak.takscheduler.api.controller.subscription;


import com.simpletak.takscheduler.api.config.Response;
import com.simpletak.takscheduler.api.dto.subscription.SubscriptionRequestDTO;
import com.simpletak.takscheduler.api.dto.subscription.SubscriptionResponseDTO;
import com.simpletak.takscheduler.api.service.subscription.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/subscription")
@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user subscribed to the event group",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Such user/'event group' not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Such subscription already exists",
                    content = @Content)
    })
    @Operation(summary = "Subscribe to user group")
    @PostMapping
    public Response<SubscriptionResponseDTO> subscribe(@Valid @RequestBody SubscriptionRequestDTO subscriptionDTO) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return Response.success(subscriptionService.subscribe(subscriptionDTO, userId));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user unsubscribed from the event group",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Such subscription not found",
                    content = @Content)
    })
    @DeleteMapping
    public Response<String> unsubscribe(@Valid @RequestBody SubscriptionRequestDTO subscriptionDTO) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
        subscriptionService.unsubscribe(subscriptionDTO, userId);
        return Response.success(null);
    }
}

package com.simpletak.takscheduler.api.controller.subscription;


import com.simpletak.takscheduler.api.config.Response;
import com.simpletak.takscheduler.api.dto.subscription.SubscriptionDTO;
import com.simpletak.takscheduler.api.service.subscription.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/subscription")
@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user subscribed to the event group",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Such user/'event group' not found",
                    content = @Content)
    })
    @Operation(summary = "Subscribe to user group")
    @PostMapping
    public Response<SubscriptionDTO> subscribe(@Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        return Response.success(subscriptionService.subscribe(subscriptionDTO));
    }
}

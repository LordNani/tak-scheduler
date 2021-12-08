package com.simpletak.takscheduler.api.dto.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponseDTO {
    private UUID id;
    private UUID userId;
    private UUID eventGroupID;
}

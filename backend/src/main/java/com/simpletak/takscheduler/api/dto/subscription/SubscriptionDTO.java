package com.simpletak.takscheduler.api.dto.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
    private UUID id;
    @NotNull
    private UUID eventGroupID;
    @NotNull
    private UUID userID;
}

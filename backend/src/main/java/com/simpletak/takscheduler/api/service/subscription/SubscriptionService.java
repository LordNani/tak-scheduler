package com.simpletak.takscheduler.api.service.subscription;

import com.simpletak.takscheduler.api.dto.subscription.SubscriptionRequestDTO;
import com.simpletak.takscheduler.api.dto.subscription.SubscriptionResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
public interface SubscriptionService {
    SubscriptionResponseDTO subscribe(SubscriptionRequestDTO subscriptionDTO, UUID userId);

    void unsubscribe(SubscriptionRequestDTO subscriptionRequestDTO, UUID userId);
}

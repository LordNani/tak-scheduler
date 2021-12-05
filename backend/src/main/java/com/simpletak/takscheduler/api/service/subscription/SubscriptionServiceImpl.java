package com.simpletak.takscheduler.api.service.subscription;

import com.simpletak.takscheduler.api.dto.subscription.SubscriptionMapper;
import com.simpletak.takscheduler.api.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.dto.subscription.SubscriptionDTO;
import com.simpletak.takscheduler.api.model.subscription.SubscriptionEntity;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.api.repository.subscription.SubscriptionRepository;
import com.simpletak.takscheduler.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final EventGroupRepository eventGroupRepository;
    private final SubscriptionMapper subscriptionMapper;
    @Override
    public SubscriptionDTO subscribe(SubscriptionDTO dto) {
        UserEntity user = userRepository
                .findById(dto.getUserID())
                .orElseThrow(UserNotFoundException::new);

        EventGroupEntity eventGroup = eventGroupRepository
                .findById(dto.getEventGroupID())
                .orElseThrow(UserNotFoundException::new);
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(eventGroup, user);

        return subscriptionMapper.fromEntity(subscriptionRepository.save(subscriptionEntity));
    }
}

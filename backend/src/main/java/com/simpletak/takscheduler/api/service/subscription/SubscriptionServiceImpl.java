package com.simpletak.takscheduler.api.service.subscription;

import com.simpletak.takscheduler.api.dto.subscription.SubscriptionMapper;
import com.simpletak.takscheduler.api.dto.subscription.SubscriptionResponseDTO;
import com.simpletak.takscheduler.api.exception.eventgroup.EventGroupNotFoundException;
import com.simpletak.takscheduler.api.exception.subscription.SubscriptionAlreadyExistsException;
import com.simpletak.takscheduler.api.exception.subscription.SubscriptionNotFoundException;
import com.simpletak.takscheduler.api.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.dto.subscription.SubscriptionRequestDTO;
import com.simpletak.takscheduler.api.model.subscription.SubscriptionEntity;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.api.repository.subscription.SubscriptionRepository;
import com.simpletak.takscheduler.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final EventGroupRepository eventGroupRepository;
    private final SubscriptionMapper subscriptionMapper;
    @Override
    public SubscriptionResponseDTO subscribe(SubscriptionRequestDTO dto, UUID userId) {
        if(subscriptionRepository.existsByEventGroupEntity_IdAndUserEntity_Id(dto.getEventGroupID(), userId)){
            throw new SubscriptionAlreadyExistsException();
        }
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);

        EventGroupEntity eventGroup = eventGroupRepository
                .findById(dto.getEventGroupID())
                .orElseThrow(EventGroupNotFoundException::new);
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(eventGroup, user);

        return subscriptionMapper.fromEntity(subscriptionRepository.save(subscriptionEntity));
    }

    @Override
    public void unsubscribe(SubscriptionRequestDTO dto, UUID userId) {
        if(!subscriptionRepository.existsByEventGroupEntity_IdAndUserEntity_Id(dto.getEventGroupID(), userId)){
            throw new SubscriptionNotFoundException();
        }
        subscriptionRepository.deleteByEventGroupEntity_IdAndUserEntity_Id(dto.getEventGroupID(), userId);
    }
}

package com.simpletak.takscheduler.api.dto.subscription;

import com.simpletak.takscheduler.api.dto.Mapper;
import com.simpletak.takscheduler.api.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.model.subscription.SubscriptionEntity;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import com.simpletak.takscheduler.api.repository.eventGroup.EventGroupRepository;
import com.simpletak.takscheduler.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper implements Mapper<SubscriptionEntity, SubscriptionDTO> {
    private final UserRepository userRepository;
    private final EventGroupRepository eventGroupRepository;
    @Override
    public SubscriptionEntity toEntity(SubscriptionDTO dto) {
        UserEntity user = userRepository
                .findById(dto.getUserID())
                .orElseThrow(UserNotFoundException::new);

        EventGroupEntity eventGroup = eventGroupRepository
                .findById(dto.getEventGroupID())
                .orElseThrow(UserNotFoundException::new);
        return new SubscriptionEntity(dto.getId(), eventGroup, user);
    }

    @Override
    public SubscriptionDTO fromEntity(SubscriptionEntity entity) {
        return new SubscriptionDTO(
                entity.getId(),
                entity.getEventGroupEntity().getId(),
                entity.getUserEntity().getId()
        );
    }
}

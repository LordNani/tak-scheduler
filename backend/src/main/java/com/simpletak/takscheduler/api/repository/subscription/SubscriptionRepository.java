package com.simpletak.takscheduler.api.repository.subscription;

import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.model.subscription.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID> {
    boolean existsByEventGroupEntity_IdAndUserEntity_Id(UUID eventGroupId, UUID userId);

    void deleteByEventGroupEntity_IdAndUserEntity_Id(UUID eventGroupId, UUID userId);

    List<SubscriptionEntity> findAllByUserEntity_Id(UUID userId);

    void deleteByEventGroupEntity_Id(UUID id);
}

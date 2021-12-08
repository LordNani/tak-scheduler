package com.simpletak.takscheduler.api.repository.subscription;

import com.simpletak.takscheduler.api.model.subscription.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID> {
    boolean existsByEventGroupEntity_IdAndUserEntity_Id(UUID eventGroupId, UUID userId);

    void deleteByEventGroupEntity_IdAndUserEntity_Id(UUID eventGroupId, UUID userId);
}

package com.simpletak.takscheduler.api.repository.eventGroup;

import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.model.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface EventGroupRepositoryPagingAndSorting extends PagingAndSortingRepository<EventGroupEntity, UUID> {
    Page<EventGroupEntity> findAllByOwner(UserEntity owner, Pageable pageable);
}

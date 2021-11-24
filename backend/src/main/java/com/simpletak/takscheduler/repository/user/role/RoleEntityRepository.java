package com.simpletak.takscheduler.repository.user.role;

import com.simpletak.takscheduler.dto.user.role.RoleDTO;
import com.simpletak.takscheduler.model.user.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByName(String name);

    List<RoleEntity> getAllByIdNotNull();
}

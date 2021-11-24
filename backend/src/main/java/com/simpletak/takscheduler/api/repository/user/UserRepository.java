package com.simpletak.takscheduler.api.repository.user;

import com.simpletak.takscheduler.api.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findById(UUID userId);
    Optional<UserEntity> findUserEntityByUsernameAndPassword(String username, String password);
    Optional<UserEntity> findUserEntityByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByRoleEntity_Name(String roleName);
}

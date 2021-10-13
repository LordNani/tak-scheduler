package com.simpletak.takscheduler.repository.user;

import com.simpletak.takscheduler.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findById(UUID userId);
    Optional<UserEntity> findUserEntityByUsernameAndPassword(String username, String password);
}

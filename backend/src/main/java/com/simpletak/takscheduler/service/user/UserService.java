package com.simpletak.takscheduler.service.user;

import com.simpletak.takscheduler.config.security.jwt.JwtProviderImpl;
import com.simpletak.takscheduler.dto.user.*;
import com.simpletak.takscheduler.exception.user.PasswordIsIncorrectException;
import com.simpletak.takscheduler.exception.user.SuchUserAlreadyExistsException;
import com.simpletak.takscheduler.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.exception.user.role.RoleNotFoundException;
import com.simpletak.takscheduler.model.user.role.RoleEntity;
import com.simpletak.takscheduler.model.user.UserEntity;
import com.simpletak.takscheduler.repository.user.UserRepository;
import com.simpletak.takscheduler.repository.user.role.RoleEntityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProviderImpl jwtProviderImpl;
    private final RoleEntityRepository roleEntityRepository;

    @Transactional
    public SignupUserResponseDTO registerUser(SignupUserRequestDTO signupUserRequestDTO) {
        boolean isExists = userRepository.existsByUsername(signupUserRequestDTO.getUsername());

        if (isExists) {
            throw new SuchUserAlreadyExistsException();
        } else {
            RoleEntity userRole = roleEntityRepository.findByName("ROLE_USER").orElseThrow(RoleNotFoundException::new);
            UserEntity userToSave = UserEntity
                    .builder()
                    .username(signupUserRequestDTO.getUsername())
                    .fullName(signupUserRequestDTO.getFullName())
                    .roleEntity(userRole)
                    .password(generateHashedPassword(signupUserRequestDTO.getPassword()))
                    .build();

            userRepository.saveAndFlush(userToSave);
            return new SignupUserResponseDTO(null, getUser(userToSave.getId()));
        }
    }

    @Transactional
    public AuthTokenDTO signinUser(SigninUserRequestDTO userEntity) {
        String inputPassword = userEntity.getPassword();
        UserEntity existingUser = userRepository.findUserEntityByUsername(userEntity.getUsername()).orElseThrow(UserNotFoundException::new);
        String dbPassword = existingUser.getPassword();

        if (passwordEncoder.matches(inputPassword, dbPassword)) {
            String token = jwtProviderImpl.generateToken(userEntity.getUsername(), existingUser.getRoleEntity().getName());
            return new AuthTokenDTO(token);
        } else {
            throw new PasswordIsIncorrectException();
        }
    }

    public UserInfoResponseDTO getUser(UUID id) {
        Optional<UserEntity> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();
            return new UserInfoResponseDTO(user.getId(), user.getUsername(), user.getFullName());
        } else throw new UserNotFoundException();
    }

    @Transactional
    public EditUserResponseDTO editUser(EditUserRequestDTO editUserRequestDTO) {
        UserEntity user = userRepository.findById(editUserRequestDTO.getUserId()).orElseThrow(UserNotFoundException::new);

        user.setUsername(editUserRequestDTO.getUsername());
        user.setFullName(editUserRequestDTO.getFullName());
        userRepository.saveAndFlush(user);
        return new EditUserResponseDTO(user.getUsername(), user.getFullName());
    }

    @Transactional
    public void deleteUser(DeleteUserRequestDTO deleteUserRequestDTO) {
        Optional<UserEntity> existingUser = userRepository.findById(deleteUserRequestDTO.getUserId());
        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();
            if (isPasswordCorrect(deleteUserRequestDTO.getPassword(), user.getPassword())) {
                userRepository.deleteById(user.getId());
                userRepository.flush();
            } else throw new PasswordIsIncorrectException();
        } else throw new UserNotFoundException();
    }


    private static boolean isPasswordCorrect(String password, String hash) {
        return password.equals(hash);
    }

    private String generateHashedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public UserEntity findByLogin(String username) {
        return userRepository.findUserEntityByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public boolean adminExists(){
        return userRepository.existsByRoleEntity_Name("ROLE_ADMIN");
    }

    public SignupUserRequestDTO createAdmin(){
        String username = RandomStringUtils.random(8, true, false);
        String password = RandomStringUtils.random(20, true, true);
        String name = "ADMIN";

        RoleEntity userRole = roleEntityRepository.findByName("ROLE_ADMIN").orElseThrow(RoleNotFoundException::new);
        UserEntity userToSave = UserEntity
                .builder()
                .username(username)
                .fullName(name)
                .roleEntity(userRole)
                .password(generateHashedPassword(password))
                .build();

        userRepository.saveAndFlush(userToSave);
        return new SignupUserRequestDTO(name, username, password);
    }
}

package com.simpletak.takscheduler.service.userService;

import com.simpletak.takscheduler.dto.user.SignupUserDTO;
import com.simpletak.takscheduler.model.user.UserEntity;
import com.simpletak.takscheduler.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void register(SignupUserDTO signupUserDTO){
        UserEntity exampleUser = new UserEntity();
        exampleUser.setUsername(signupUserDTO.getUsername());
        Example<UserEntity> example = Example.of(exampleUser);
        Optional<UserEntity> existingUser = userRepository.findOne(example);
        if(existingUser.isPresent()){
            throw new IllegalArgumentException("Such user already exists");
        }
        else{
            UserEntity userToSave = UserEntity
                    .builder()
                    .username(signupUserDTO.getUsername())
                    .fullName(signupUserDTO.getFullName())
                    .password(signupUserDTO.getPassword())
                    .build();

            userRepository.saveAndFlush(userToSave);
        }
    }
}

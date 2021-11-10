package com.simpletak.takscheduler.service.user;

import com.simpletak.takscheduler.dto.user.*;
import com.simpletak.takscheduler.exception.user.PasswordIsIncorrectException;
import com.simpletak.takscheduler.exception.user.SuchUserAlreadyExistsException;
import com.simpletak.takscheduler.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.model.user.UserEntity;
import com.simpletak.takscheduler.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public SignupUserResponseDTO registerUser(SignupUserRequestDTO signupUserRequestDTO){
        UserEntity exampleUser = new UserEntity();
        exampleUser.setUsername(signupUserRequestDTO.getUsername());
        Example<UserEntity> example = Example.of(exampleUser);
        Optional<UserEntity> existingUser = userRepository.findOne(example);
        if(existingUser.isPresent()){
            throw new SuchUserAlreadyExistsException();
        }
        else{
            UserEntity userToSave = UserEntity
                    .builder()
                    .username(signupUserRequestDTO.getUsername())
                    .fullName(signupUserRequestDTO.getFullName())
                    .password(signupUserRequestDTO.getPassword())
                    .build();

            userRepository.saveAndFlush(userToSave);
            return new SignupUserResponseDTO(null, getUser(userToSave.getId()));
        }
    }

    public AuthTokenDTO signinUser(SigninUserRequestDTO request){
        String hashedPassword = generateHashedPassword(request.getPassword());
        Optional<UserEntity> existingUser =
                userRepository.findUserEntityByUsernameAndPassword(request.getUsername(), hashedPassword);
        if(existingUser.isPresent()){
            return null;
        }
        else throw new PasswordIsIncorrectException();
    }

    public UserInfoResponseDTO getUser(UUID id) {
        Optional<UserEntity> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()) {
            UserEntity user = existingUser.get();
            return new UserInfoResponseDTO(user.getId(), user.getUsername(), user.getFullName());
        }
        else throw new UserNotFoundException();
    }

    public EditUserResponseDTO editUser(EditUserRequestDTO editUserRequestDTO){
        Optional<UserEntity> existingUser = userRepository.findById(editUserRequestDTO.getUserId());
//        existingUser.orElseThrow()
        if(existingUser.isPresent()){
            UserEntity user = existingUser.get();
            user.setUsername(editUserRequestDTO.getUsername());
            user.setFullName(editUserRequestDTO.getFullName());
            userRepository.saveAndFlush(user);
            return new EditUserResponseDTO(user.getUsername(), user.getFullName());
        }
        else throw new UserNotFoundException();
    }

    public void deleteUser(DeleteUserRequestDTO deleteUserRequestDTO){
        Optional<UserEntity> existingUser = userRepository.findById(deleteUserRequestDTO.getUserId());
        if(existingUser.isPresent()){
            UserEntity user = existingUser.get();
            if(isPasswordCorrect(deleteUserRequestDTO.getPassword(), user.getPassword())){
                userRepository.deleteById(user.getId());
                userRepository.flush();
            }
            else throw new PasswordIsIncorrectException();
        }
        else throw new UserNotFoundException();
    }




    private static boolean isPasswordCorrect(String password, String hash) {
        return password.equals(hash);
    }

    private static String generateHashedPassword(String password) {
        return password;
    }
}

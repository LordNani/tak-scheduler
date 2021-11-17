package com.simpletak.takscheduler.service.authentication;

import com.simpletak.takscheduler.model.user.CustomUserDetails;
import com.simpletak.takscheduler.model.user.UserEntity;
import com.simpletak.takscheduler.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByLogin(username);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}

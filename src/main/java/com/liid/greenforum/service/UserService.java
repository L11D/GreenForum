package com.liid.greenforum.service;

import com.liid.greenforum.entity.UserEntity;
import com.liid.greenforum.model.user.JwtResponse;
import com.liid.greenforum.model.user.UserLoginRequest;
import com.liid.greenforum.model.user.UserProfileResponse;
import com.liid.greenforum.model.user.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface UserService {
    boolean userExist(UUID id);
    UserEntity findUserById(UUID id);
    JwtResponse registerUser(@Valid UserRegistrationRequest registrationRequest);
    JwtResponse login(@Valid UserLoginRequest request);
    UserProfileResponse getProfile(Authentication authentication);
}


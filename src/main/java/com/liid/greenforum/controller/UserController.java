package com.liid.greenforum.controller;

import com.liid.greenforum.model.user.JwtResponse;
import com.liid.greenforum.model.user.UserLoginRequest;
import com.liid.greenforum.model.user.UserProfileResponse;
import com.liid.greenforum.model.user.UserRegistrationRequest;
import com.liid.greenforum.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("register")
    @ResponseBody
    public JwtResponse registerUser(@RequestBody UserRegistrationRequest request){
        return userService.registerUser(request);
    }

    @GetMapping("profile")
    @ResponseBody
    public UserProfileResponse get(Authentication authentication) {
        return userService.getProfile(authentication);
    }


    @PostMapping("login")
    @ResponseBody
    public JwtResponse login(@RequestBody UserLoginRequest request){
        return userService.login(request);
    }
}
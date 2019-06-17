package com.gullycricket.authservice.controller;

import com.gullycricket.authservice.domain.UserData;
import com.gullycricket.authservice.exception.ResourceNotFoundException;
import com.gullycricket.authservice.model.User;
import com.gullycricket.authservice.repository.UserDetailsRepository;
import com.gullycricket.authservice.repository.UserRepository;
import com.gullycricket.authservice.security.CurrentUser;
import com.gullycricket.authservice.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasRole('USER')")
    public UserData getUserData(@CurrentUser UserPrincipal userPrincipal) {
        UserData userData = new UserData();

        userRepository.findById(userPrincipal.getId()).ifPresent(
                user -> {
                    userData.setId(user.getId());
                    userData.setImageUrl(user.getImageUrl());
                    userData.setName(user.getName());
                    userDetailsRepository.findByUserId(user.getId()).ifPresent(
                            userDetails -> {
                                userData.setMomPoints(userDetails.getMomPoints());
                                userData.setPoints(userDetails.getPoints());
                            }
                    );
                }
        );

        return userData;
    }

    @GetMapping("/active_userids")
    public List<Long> getActiveUsersCount() {
        List<Long> activeUserIds = new ArrayList<>();
        userRepository.findByActive(1).forEach(user -> {
            activeUserIds.add(user.getId());
        });
        return activeUserIds;
    }

    @GetMapping("/active_users")
    public List<User> getActiveUsers() {
        return userRepository.findByActive(1);
    }
}

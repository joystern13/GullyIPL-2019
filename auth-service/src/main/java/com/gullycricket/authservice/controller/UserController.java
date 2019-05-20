package com.gullycricket.authservice.controller;

import com.gullycricket.authservice.exception.ResourceNotFoundException;
import com.gullycricket.authservice.model.User;
import com.gullycricket.authservice.repository.UserRepository;
import com.gullycricket.authservice.security.CurrentUser;
import com.gullycricket.authservice.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/hi")
    public String sayHello(){
        return "Hello World!";
    }
}

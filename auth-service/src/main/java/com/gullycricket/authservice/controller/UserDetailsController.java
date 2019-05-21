package com.gullycricket.authservice.controller;


import com.google.api.services.sqladmin.SQLAdmin;
import com.gullycricket.authservice.domain.UserData;
import com.gullycricket.authservice.model.User;
import com.gullycricket.authservice.model.UserDetails;
import com.gullycricket.authservice.repository.UserDetailsRepository;
import com.gullycricket.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("/user_details")
public class UserDetailsController {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    UserRepository userRepository;

    private UserData initializeUserData(User user, UserDetails userDetails) {
        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setName(user.getName());
        userData.setImageUrl(user.getImageUrl());
        userData.setPoints(userDetails.getPoints());
        userData.setMomPoints(userDetails.getMomPoints());
        return userData;
    }

    @GetMapping("/top10/{leg_id}")
    @PreAuthorize("hasRole('USER')")
    public List<UserData> getTop10(@PathVariable(name = "leg_id") final Long legId) {

        List<UserData> userDataList = new ArrayList<>();

        userDetailsRepository.findTop10ByLegIdOrderByPointsDesc(legId).forEach(userDetails ->
                userRepository.findById(userDetails.getUserId()).ifPresent(user ->
                        userDataList.add(initializeUserData(user, userDetails))
                )
        );

        return userDataList;
    }

    @GetMapping("/users/{leg_id}")
    @PreAuthorize("hasRole('USER')")
    public List<UserData> getUsers(@PathVariable(name = "leg_id") final Long legId) {

        List<UserData> userDataList = new ArrayList<>();

        userDetailsRepository.findAllByLegId(legId).forEach(userDetails ->
                userRepository.findById(userDetails.getUserId()).ifPresent(user ->
                        userDataList.add(initializeUserData(user, userDetails))
                )
        );

        return userDataList;
    }

    @GetMapping("/allusers")
    @PreAuthorize("hasRole('USER')")
    public List<UserData> getAllUsers(){
        List<UserData> userDataList = new ArrayList<>();
        userDetailsRepository.findAll().forEach(userDetails ->
                userRepository.findById(userDetails.getUserId()).ifPresent(user ->
                        userDataList.add(initializeUserData(user,userDetails))
                )
        );
        return userDataList;
    }

}
package com.gullyipl.userservice.resource;

import com.gullyipl.userservice.model.UserInfo;
import com.gullyipl.userservice.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/gullyipl/user-info")
public class UserInfoResource {

    @Autowired
    UserInfoRepository userInfoRepository;

    @GetMapping(value = "/getallusers")
    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }

    @GetMapping(value = "/getuser/{userid}")
    public UserInfo getUserById(@PathVariable("userid") final int userId) {
        if (userInfoRepository.findById(userId).isPresent()) {
            return userInfoRepository.findById(userId).get();
        } else return null;
    }

    @PostMapping(value = "/adduser")
    public String addUser(@RequestBody final UserInfo userInfo) {
        if (userInfoRepository.findByUserName(userInfo.getUserName()).isPresent()) {
            return "There is already an existing user registered with this username";
        } else {
            userInfoRepository.save(userInfo);
            return "You have been successfully registered!";
        }
    }
}

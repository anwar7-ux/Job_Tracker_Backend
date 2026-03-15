package com.job.controller;

import com.job.dto.UserRequest;
import com.job.dto.UserResponse;
import com.job.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // GET PROFILE
    @GetMapping("/profile")
    public UserResponse getProfile() {
        String username = SecurityContextHolder
            .getContext().getAuthentication().getName();
        return userService.getUserProfile(username);
    }

    // UPDATE PROFILE
    @PutMapping("/update")
    public String updateProfile(
            @RequestBody UserRequest request) {
        String username = SecurityContextHolder
            .getContext().getAuthentication().getName();
        return userService.updateUserProfile(username, request);
    }

    // CHANGE PASSWORD
    @PutMapping("/change-password")
    public String changePassword(
            @RequestBody Map<String, String> request) {
        String username = SecurityContextHolder
            .getContext().getAuthentication().getName();
        return userService.changePassword(
            username,
            request.get("oldPassword"),
            request.get("newPassword")
        );
    }
}
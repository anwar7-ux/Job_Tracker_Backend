package com.job.service;

import com.job.dto.UserRequest;
import com.job.dto.UserResponse;
import com.job.entity.User;
import com.job.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // GET PROFILE
    public UserResponse getUserProfile(String username) {
        User user = userRepository.findByUsername(username);
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setUserEmail(
            user.getUserInformation().getUserEmail());
        response.setUserPhone(
            user.getUserInformation().getUserPhone());
        response.setActive(user.isActive());
        response.setRole(user.getRole());
        return response;
    }

    // UPDATE PROFILE
    public String updateUserProfile(
            String username, UserRequest request) {
        User user = userRepository.findByUsername(username);
        if (user == null) return "User not found!";
        user.setUsername(request.getUsername());
        user.getUserInformation()
            .setUserEmail(request.getUserEmail());
        user.getUserInformation()
            .setUserPhone(request.getUserPhone());
        userRepository.save(user);
        return "Profile updated!";
    }

    // CHANGE PASSWORD
    public String changePassword(
            String username,
            String oldPassword,
            String newPassword) {

        User user = userRepository.findByUsername(username);
        if (user == null) return "User not found!";

        // CHECK OLD PASSWORD MATCHES!
        boolean matches = passwordEncoder.matches(
            oldPassword,
            user.getUserInformation().getUserPassword()
        );

        if (!matches) return "Old password is incorrect!";

        // SAVE NEW PASSWORD BCRYPT ENCODED!
        user.getUserInformation().setUserPassword(
            passwordEncoder.encode(newPassword)
        );
        userRepository.save(user);
        return "Password changed successfully!";
    }
}
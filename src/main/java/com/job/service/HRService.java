package com.job.service;

import com.job.dto.UserRequest;
import com.job.entity.User;
import com.job.entity.UserInformation;
import com.job.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class HRService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // HR REGISTERS ADMIN
    public String registerAdmin(UserRequest request) {
        // CHECK IF USERNAME ALREADY EXISTS!
        User existing = userRepository
            .findByUsername(request.getUsername());
        if (existing != null) 
            return "Username already exists!";

        User user = new User();
        user.setUsername(request.getUsername());
        user.setActive(true);
        user.setRole("ADMIN"); // ← ALWAYS ADMIN!

        UserInformation userInformation = new UserInformation();
        userInformation.setUserEmail(request.getUserEmail());
        userInformation.setUserPhone(request.getUserPhone());
        userInformation.setUserPassword(
            passwordEncoder.encode(request.getUserPassword()));
        userInformation.setCreatedAt(LocalDateTime.now());

        userInformation.setUser(user);
        user.setUserInformation(userInformation);
        userRepository.save(user);

        // SEND WELCOME EMAIL TO ADMIN!
        emailService.sendWelcomeEmail(
            request.getUserEmail(),
            request.getUsername()
        );

        return "Admin registered successfully!";
    }

    // GET ALL ADMINS
    public String deleteAdmin(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return "User not found!";
        if (!user.getRole().equals("ADMIN")) 
            return "User is not an admin!";
        userRepository.deleteById(id);
        return "Admin deleted successfully!";
    }
}
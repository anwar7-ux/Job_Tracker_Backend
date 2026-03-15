package com.job.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.job.dto.LoginRequest;
import com.job.dto.UserRequest;
import com.job.entity.User;
import com.job.entity.UserInformation;
import com.job.repository.UserRepository;
import com.job.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    public String register(UserRequest userRequest) {

        // ✅ Check if username already exists BEFORE saving
        User existingUser = userRepository.findByUsername(userRequest.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setActive(true);
        user.setRole("USER");

        UserInformation userInformation = new UserInformation();
        userInformation.setUserEmail(userRequest.getUserEmail());
        userInformation.setUserPhone(userRequest.getUserPhone());
        userInformation.setUserPassword(
            passwordEncoder.encode(userRequest.getUserPassword()));
        userInformation.setCreatedAt(LocalDateTime.now());
        userInformation.setUser(user);
        user.setUserInformation(userInformation);

        userRepository.save(user);

        // ✅ Email wrapped in try-catch so a mail failure never crashes registration
        try {
            emailService.sendWelcomeEmail(
                userRequest.getUserEmail(),
                userRequest.getUsername()
            );
        } catch (Exception e) {
            System.err.println("Welcome email failed (registration still succeeded): "
                + e.getMessage());
        }

        return "User registered successfully";
    }

    public String login(LoginRequest loginRequest) {

        // ✅ Find user by username
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // ✅ Check if account is active
        if (!user.isActive()) {
            throw new RuntimeException("Account is disabled. Please contact admin.");
        }

        // ✅ Validate password
        boolean passwordMatch = passwordEncoder.matches(
            loginRequest.getUserPassword(),
            user.getUserInformation().getUserPassword()
        );
        if (!passwordMatch) {
            throw new RuntimeException("Invalid password");
        }

        // ✅ Generate and return JWT token
        return jwtUtil.generateToken(user.getUsername());
    }
}
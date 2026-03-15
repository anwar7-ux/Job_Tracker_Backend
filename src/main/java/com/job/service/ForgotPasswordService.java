package com.job.service;

import com.job.entity.User;
import com.job.entity.UserInformation;
import com.job.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // STEP 1: Send OTP to email
    public String sendOtp(String email) {
        // Find user by email
        User user = userRepository.findAll().stream()
                .filter(u -> u.getUserInformation() != null &&
                        email.equals(u.getUserInformation().getUserEmail()))
                .findFirst()
                .orElse(null);

        if (user == null) {
            throw new RuntimeException("No account found with this email!");
        }

        if (!user.isActive()) {
            throw new RuntimeException("Your account is disabled. Contact admin.");
        }

        String otp = otpService.generateOtp(email);
        emailService.sendOtpEmail(email, otp);
        return "OTP sent to your email!";
    }

    // STEP 2: Verify OTP
    public String verifyOtp(String email, String otp) {
        boolean valid = otpService.verifyOtp(email, otp);
        if (!valid) {
            throw new RuntimeException("Invalid or expired OTP!");
        }
        otpService.markEmailVerified(email);
        return "OTP verified successfully!";
    }

    // STEP 3: Reset Password
    public String resetPassword(String email, String newPassword) {
        if (!otpService.isEmailVerified(email)) {
            throw new RuntimeException("Please verify OTP first!");
        }

        User user = userRepository.findAll().stream()
                .filter(u -> u.getUserInformation() != null &&
                        email.equals(u.getUserInformation().getUserEmail()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found!"));

        UserInformation info = user.getUserInformation();
        info.setUserPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpService.clearVerifiedEmail(email);
        return "Password reset successfully!";
    }
}
package com.job.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    // Store OTP in memory: email -> otp
    private final Map<String, String> otpStore = new HashMap<>();

    public String generateOtp(String email) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        otpStore.put(email, otp);
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        String stored = otpStore.get(email);
        if (stored != null && stored.equals(otp)) {
            otpStore.remove(email); // ✅ OTP can only be used once
            return true;
        }
        return false;
    }

    // ✅ Keep email verified so password reset can proceed
    private final Map<String, Boolean> verifiedEmails = new HashMap<>();

    public void markEmailVerified(String email) {
        verifiedEmails.put(email, true);
    }

    public boolean isEmailVerified(String email) {
        return verifiedEmails.getOrDefault(email, false);
    }

    public void clearVerifiedEmail(String email) {
        verifiedEmails.remove(email);
    }
}
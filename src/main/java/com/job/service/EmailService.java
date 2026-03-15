package com.job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to Job Tracker!");
        message.setText(
            "Hi " + username + "!\n\n" +
            "Welcome to Job Application Tracker!\n" +
            "Your account has been created successfully!\n\n" +
            "Start tracking your job applications now!\n\n" +
            "Best Regards,\n" +
            "Job Tracker Team"
        );
        mailSender.send(message);
    }

    public void sendJobCreatedEmail(
            String toEmail, String username, String companyName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("New Job Application Created!");
        message.setText(
            "Hi " + username + "!\n\n" +
            "Your job application for " + companyName +
            " has been created successfully!\n\n" +
            "Status: APPLIED\n\n" +
            "Best Regards,\n" +
            "Job Tracker Team"
        );
        mailSender.send(message);
    }

    public void sendStatusUpdateEmail(
            String toEmail, String username,
            String companyName, String newStatus) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Job Status Updated - " + companyName);
        message.setText(
            "Hi " + username + "!\n\n" +
            "Your job application status for " +
            companyName + " has been updated!\n\n" +
            "New Status: " + newStatus + "\n\n" +
            "Best Regards,\n" +
            "Job Tracker Team"
        );
        mailSender.send(message);
    }

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your Password Reset OTP");
            message.setText(
                    "Your OTP for password reset is: " + otp + "\n\n" +
                            "This OTP is valid for one use only.\n" +
                            "If you did not request this, please ignore this email."
            );
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send OTP email: " + e.getMessage());
        }
    }
}
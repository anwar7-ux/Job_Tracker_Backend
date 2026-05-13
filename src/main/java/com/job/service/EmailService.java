package com.job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("no.reply.app.jobtracker@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);

            System.out.println("✅ Email sent to: " + toEmail);

        } catch (Exception e) {
            System.out.println("❌ Failed to send email: " + e.getMessage());
        }
    }

    public void sendWelcomeEmail(String toEmail, String username) {
        sendEmail(
                toEmail,
                "Welcome to Job Tracker!",
                "Hi " + username + "!\n\n" +
                        "Welcome to Job Application Tracker!\n" +
                        "Your account has been created successfully!\n\n" +
                        "Best Regards,\nJob Tracker Team"
        );
    }

    public void sendJobCreatedEmail(String toEmail, String username, String companyName) {
        sendEmail(
                toEmail,
                "New Job Application Created!",
                "Hi " + username + "!\n\n" +
                        "Your job application for " + companyName +
                        " has been created successfully!\n\n" +
                        "Status: APPLIED\n\n" +
                        "Best Regards,\nJob Tracker Team"
        );
    }

    public void sendStatusUpdateEmail(String toEmail, String username,
                                      String companyName, String newStatus) {

        sendEmail(
                toEmail,
                "Job Status Updated - " + companyName,
                "Hi " + username + "!\n\n" +
                        "Your application status for " + companyName +
                        " has been updated!\n\n" +
                        "New Status: " + newStatus + "\n\n" +
                        "Best Regards,\nJob Tracker Team"
        );
    }

    public void sendApplicationReceivedEmail(String toEmail, String username,
                                             String jobTitle, String companyName) {

        sendEmail(
                toEmail,
                "Application Received - " + jobTitle,
                "Hi " + username + "!\n\n" +
                        "Your application for " + jobTitle +
                        " at " + companyName + " has been received!\n\n" +
                        "Status: PENDING\n\n" +
                        "Best Regards,\nJob Tracker Team"
        );
    }

    public void sendApplicationStatusEmail(String toEmail, String username,
                                           String jobTitle, String companyName,
                                           String newStatus) {

        sendEmail(
                toEmail,
                "Application Status Updated - " + jobTitle,
                "Hi " + username + "!\n\n" +
                        "Your application status for " + jobTitle +
                        " at " + companyName + " has been updated!\n\n" +
                        "New Status: " + newStatus + "\n\n" +
                        "Best Regards,\nJob Tracker Team"
        );
    }

    public void sendOtpEmail(String toEmail, String otp) {

        sendEmail(
                toEmail,
                "Your Password Reset OTP",
                "Your OTP for password reset is: " + otp + "\n\n" +
                        "This OTP is valid for one use only.\n" +
                        "If you did not request this, please ignore this email."
        );
    }
}
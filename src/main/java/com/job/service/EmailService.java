package com.job.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendgridApiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    private final RestTemplate restTemplate = new RestTemplate();

    private void sendEmail(String toEmail, String subject, String body) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(sendgridApiKey);

            Map<String, String> from = new HashMap<>();
            from.put("email", fromEmail);
            from.put("name", "Job Tracker");

            Map<String, String> toMap = new HashMap<>();
            toMap.put("email", toEmail);

            Map<String, Object> personalization = new HashMap<>();
            personalization.put("to", List.of(toMap));
            personalization.put("subject", subject);

            Map<String, String> content = new HashMap<>();
            content.put("type", "text/plain");
            content.put("value", body);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("from", from);
            requestBody.put("personalizations", List.of(personalization));
            requestBody.put("content", List.of(content));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            restTemplate.postForObject(
                    "https://api.sendgrid.com/v3/mail/send",
                    request,
                    String.class
            );

            System.out.println("✅ Email sent to: " + toEmail);

        } catch (Exception e) {
            System.out.println("❌ Failed to send email: " + e.getMessage());
        }
    }

    public void sendWelcomeEmail(String toEmail, String username) {
        sendEmail(toEmail,
                "Welcome to Job Tracker!",
                "Hi " + username + "!\n\n" +
                        "Welcome to Job Application Tracker!\n" +
                        "Your account has been created successfully!\n\n" +
                        "Best Regards,\nJob Tracker Team"
        );
    }

    public void sendJobCreatedEmail(String toEmail, String username, String companyName) {
        sendEmail(toEmail,
                "New Job Application Created!",
                "Hi " + username + "!\n\n" +
                        "Your job application for " + companyName +
                        " has been created!\n\nStatus: APPLIED\n\n" +
                        "Best Regards,\nJob Tracker Team"
        );
    }

    public void sendStatusUpdateEmail(String toEmail, String username,
                                      String companyName, String newStatus) {
        sendEmail(toEmail,
                "Job Status Updated - " + companyName,
                "Hi " + username + "!\n\n" +
                        "Your application status for " + companyName +
                        " updated to: " + newStatus + "\n\n" +
                        "Best Regards,\nJob Tracker Team"
        );
    }

    public void sendApplicationReceivedEmail(String toEmail, String username,
                                             String jobTitle, String companyName) {
        sendEmail(toEmail,
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
        sendEmail(toEmail,
                "Application Status Updated - " + jobTitle,
                "Hi " + username + "!\n\n" +
                        "Your application status for " + jobTitle +
                        " at " + companyName + " updated to: " + newStatus + "\n\n" +
                        (newStatus.equals("OFFERED") ? "Congratulations! You got an offer!\n\n"
                                : newStatus.equals("REJECTED") ? "Unfortunately not selected this time.\n\n"
                                : newStatus.equals("INTERVIEW") ? "You are selected for interview!\n\n"
                                : newStatus.equals("RESUME_REVIEW") ? "Your resume is being reviewed!\n\n"
                                : "") +
                        "Best Regards,\nJob Tracker Team"
        );
    }

    public void sendOtpEmail(String toEmail, String otp) {
        sendEmail(toEmail,
                "Your Password Reset OTP",
                "Your OTP for password reset is: " + otp + "\n\n" +
                        "This OTP is valid for one use only.\n" +
                        "If you did not request this, please ignore this email."
        );
    }
}
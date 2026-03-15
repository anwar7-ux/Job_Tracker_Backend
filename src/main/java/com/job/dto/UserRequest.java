package com.job.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String userEmail;
    private String userPhone;
    private String userPassword;
    private String role; // ← ADD THIS!
}
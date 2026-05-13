package com.job.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplyResponse {
    private Long id;
    private String status;
    private LocalDate appliedDate;
    private String fullName;
    private String email;
    private String phone;
    private String experience;
    private String coverLetter;
    private String resumeLink;
    private String applicantUsername;
    private Long jobPostId;
    private String jobTitle;
    private String companyName;
}
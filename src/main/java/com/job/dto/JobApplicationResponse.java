package com.job.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationResponse {
    private Long id;
    private String companyName;
    private String jobTitle;
    private String applicationStatus;
    private LocalDate appliedDate;
    private String jobQualifications;
    private String jobStream;
    private String jobDescription;
}
package com.job.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationRequest {
    //Job Information
    private String jobQualifications;
    private String jobStream;
    private String jobDescription;


    //jobapplication
    private String companyName;
    private String jobTitle;
    private String applicationStatus;
    private LocalDate appliedDate;
}

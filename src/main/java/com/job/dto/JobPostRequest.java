package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostRequest {
    private String title;
    private String description;
    private String qualifications;
    private String stream;
    private String companyName;
    private String location;
    private String jobType; // FULL_TIME / PART_TIME / CONTRACT
}
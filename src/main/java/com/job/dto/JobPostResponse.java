package com.job.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostResponse {
    private Long id;
    private String title;
    private String description;
    private String qualifications;
    private String stream;
    private String companyName;
    private String location;
    private String jobType;
    private String status;
    private LocalDate postedDate;
    private String postedByUsername;
    private int applicantCount;
}
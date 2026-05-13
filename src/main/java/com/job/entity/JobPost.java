package com.job.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_post")
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String qualifications;

    private String stream;
    private String companyName;
    private String location;
    private String jobType; // FULL_TIME / PART_TIME / CONTRACT
    private String status;  // OPEN / CLOSED
    private LocalDate postedDate;

    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User postedBy;
}
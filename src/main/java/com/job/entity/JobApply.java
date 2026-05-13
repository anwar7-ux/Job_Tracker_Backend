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
@Table(name = "job_apply")
public class JobApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private LocalDate appliedDate;
    private String fullName;
    private String email;
    private String phone;
    private String experience;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    private String resumeLink;

    @ManyToOne
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private User applicant;
}
package com.job.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.job.entity.JobApply;
import com.job.entity.JobPost;
import com.job.entity.User;

public interface JobApplyRepository extends JpaRepository<JobApply, Long> {
    List<JobApply> findByApplicant(User applicant);
    List<JobApply> findByJobPost(JobPost jobPost);
    boolean existsByJobPostAndApplicant(JobPost jobPost, User applicant);
    long countByJobPost(JobPost jobPost);
}
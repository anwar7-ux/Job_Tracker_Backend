package com.job.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.job.entity.JobPost;
import com.job.entity.User;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findByStatus(String status);
    List<JobPost> findByPostedBy(User postedBy);
    List<JobPost> findByStatusAndTitleContainingIgnoreCase(String status, String title);
    List<JobPost> findByStatusAndStreamContainingIgnoreCase(String status, String stream);
    List<JobPost> findByStatusAndCompanyNameContainingIgnoreCase(String status, String companyName);
}
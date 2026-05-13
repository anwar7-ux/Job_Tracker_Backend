package com.job.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.job.dto.JobPostRequest;
import com.job.dto.JobPostResponse;
import com.job.service.JobPostService;

@RestController
@RequestMapping("/api/job-posts")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

    @PostMapping
    public String createJobPost(@RequestBody JobPostRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        jobPostService.createJobPost(username, request);
        return "Job posted successfully!";
    }

    @GetMapping
    public List<JobPostResponse> getAllOpenJobs() {
        return jobPostService.getAllOpenJobs();
    }

    @GetMapping("/search")
    public List<JobPostResponse> searchJobs(@RequestParam String keyword) {
        return jobPostService.searchJobs(keyword);
    }

    @GetMapping("/mine")
    public List<JobPostResponse> getMyPostedJobs() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return jobPostService.getMyPostedJobs(username);
    }

    @PutMapping("/{jobPostId}/close")
    public String closeJobPost(@PathVariable Long jobPostId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        jobPostService.closeJobPost(username, jobPostId);
        return "Job closed!";
    }

    @DeleteMapping("/{jobPostId}")
    public String deleteJobPost(@PathVariable Long jobPostId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        jobPostService.deleteJobPost(username, jobPostId);
        return "Job deleted!";
    }
}
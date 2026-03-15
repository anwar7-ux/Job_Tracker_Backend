package com.job.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.job.dto.JobApplicationRequest;
import com.job.dto.JobApplicationResponse;
import com.job.service.JobApplicationService;

@RestController
@RequestMapping("/api/jobs")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping
    public String createJob(@RequestBody JobApplicationRequest request) {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        jobApplicationService.createJob(username, request);
        return "Job application created!";
    }

    @GetMapping
    public List<JobApplicationResponse> getAllJobs() {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        return jobApplicationService.getAllJobsByUser(username);
    }

    @PutMapping("/{jobId}/status")
    public String updateStatus(
            @PathVariable Long jobId,
            @RequestParam String status) {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        jobApplicationService.updateJobStatus(username, jobId, status);
        return "Status updated!";
    }

    @DeleteMapping("/{jobId}")
    public String deleteJob(@PathVariable Long jobId) {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        jobApplicationService.deleteJob(username, jobId);
        return "Job deleted!";
    }

    @GetMapping("/status/{status}")
    public List<JobApplicationResponse> getJobsByStatus(
            @PathVariable String status) {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        return jobApplicationService.getJobsByStatus(username, status);
    }
}
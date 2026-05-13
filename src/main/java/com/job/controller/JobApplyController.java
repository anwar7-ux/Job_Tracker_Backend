package com.job.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.job.dto.JobApplyRequest;
import com.job.dto.JobApplyResponse;
import com.job.service.JobApplyService;

@RestController
@RequestMapping("/api/job-apply")
public class JobApplyController {

    @Autowired
    private JobApplyService jobApplyService;

    @PostMapping("/{jobPostId}")
    public String applyToJob(
            @PathVariable Long jobPostId,
            @RequestBody JobApplyRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return jobApplyService.applyToJob(username, jobPostId, request);
    }

    @GetMapping("/applicants/{jobPostId}")
    public List<JobApplyResponse> getApplicants(@PathVariable Long jobPostId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return jobApplyService.getApplicantsForJob(username, jobPostId);
    }

    @GetMapping("/mine")
    public List<JobApplyResponse> getMyApplications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return jobApplyService.getMyApplications(username);
    }

    @PutMapping("/{applyId}/status")
    public String updateStatus(
            @PathVariable Long applyId,
            @RequestParam String status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        jobApplyService.updateApplicationStatus(username, applyId, status);
        return "Status updated!";
    }
}
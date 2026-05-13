package com.job.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.job.dto.JobApplyRequest;
import com.job.dto.JobApplyResponse;
import com.job.entity.JobApply;
import com.job.entity.JobPost;
import com.job.entity.User;
import com.job.repository.JobApplyRepository;
import com.job.repository.JobPostRepository;
import com.job.repository.UserRepository;

@Service
public class JobApplyService {

    @Autowired
    private JobApplyRepository jobApplyRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailService emailService;

    public String applyToJob(String username, Long jobPostId, JobApplyRequest request) {
        User applicant = userRepository.findByUsername(username);
        JobPost jobPost = jobPostRepository.findById(jobPostId).orElse(null);
        if (jobPost == null) return "Job not found";
        if (jobPost.getPostedBy().getUsername().equals(username)) return "Cannot apply to your own job";
        if (jobApplyRepository.existsByJobPostAndApplicant(jobPost, applicant)) return "Already applied";
        if (jobPost.getStatus().equals("CLOSED")) return "Job is closed";

        JobApply apply = new JobApply();
        apply.setFullName(request.getFullName());
        apply.setEmail(request.getEmail());
        apply.setPhone(request.getPhone());
        apply.setExperience(request.getExperience());
        apply.setCoverLetter(request.getCoverLetter());
        apply.setResumeLink(request.getResumeLink());
        apply.setStatus("PENDING");
        apply.setAppliedDate(LocalDate.now());
        apply.setJobPost(jobPost);
        apply.setApplicant(applicant);
        jobApplyRepository.save(apply);

        notificationService.createNotification(
                jobPost.getPostedBy().getUsername(),
                username + " applied to your job: " + jobPost.getTitle()
        );

        emailService.sendApplicationReceivedEmail(
                applicant.getUserInformation().getUserEmail(),
                username,
                jobPost.getTitle(),
                jobPost.getCompanyName()
        );

        return "Applied successfully";
    }

    public List<JobApplyResponse> getApplicantsForJob(String username, Long jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId).orElse(null);
        if (jobPost == null) return new ArrayList<>();
        if (!jobPost.getPostedBy().getUsername().equals(username)) return new ArrayList<>();
        List<JobApply> applies = jobApplyRepository.findByJobPost(jobPost);
        return mapToResponse(applies);
    }

    public List<JobApplyResponse> getMyApplications(String username) {
        User applicant = userRepository.findByUsername(username);
        List<JobApply> applies = jobApplyRepository.findByApplicant(applicant);
        return mapToResponse(applies);
    }

    public void updateApplicationStatus(String username, Long applyId, String newStatus) {
        JobApply apply = jobApplyRepository.findById(applyId).orElse(null);
        if (apply == null) return;
        if (!apply.getJobPost().getPostedBy().getUsername().equals(username)) return;

        apply.setStatus(newStatus);
        jobApplyRepository.save(apply);

        notificationService.createNotification(
                apply.getApplicant().getUsername(),
                "Your application for " + apply.getJobPost().getTitle()
                        + " at " + apply.getJobPost().getCompanyName()
                        + " is now: " + newStatus
        );

        emailService.sendApplicationStatusEmail(
                apply.getApplicant().getUserInformation().getUserEmail(),
                apply.getApplicant().getUsername(),
                apply.getJobPost().getTitle(),
                apply.getJobPost().getCompanyName(),
                newStatus
        );
    }

    private List<JobApplyResponse> mapToResponse(List<JobApply> applies) {
        List<JobApplyResponse> response = new ArrayList<>();
        for (JobApply apply : applies) {
            JobApplyResponse dto = new JobApplyResponse();
            dto.setId(apply.getId());
            dto.setStatus(apply.getStatus());
            dto.setAppliedDate(apply.getAppliedDate());
            dto.setFullName(apply.getFullName());
            dto.setEmail(apply.getEmail());
            dto.setPhone(apply.getPhone());
            dto.setExperience(apply.getExperience());
            dto.setCoverLetter(apply.getCoverLetter());
            dto.setResumeLink(apply.getResumeLink());
            dto.setApplicantUsername(apply.getApplicant().getUsername());
            dto.setJobPostId(apply.getJobPost().getId());
            dto.setJobTitle(apply.getJobPost().getTitle());
            dto.setCompanyName(apply.getJobPost().getCompanyName());
            response.add(dto);
        }
        return response;
    }
}
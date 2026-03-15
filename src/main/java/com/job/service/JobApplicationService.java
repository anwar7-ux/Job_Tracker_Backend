package com.job.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.job.dto.JobApplicationRequest;
import com.job.dto.JobApplicationResponse;
import com.job.entity.JobApplication;
import com.job.entity.JobInformation;
import com.job.entity.StatusHistory;
import com.job.entity.User;
import com.job.repository.JobApplicationRepository;
import com.job.repository.StatusHistoryRepository;
import com.job.repository.UserRepository;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusHistoryRepository statusHistoryRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailService emailService;

    public void createJob(String username, JobApplicationRequest request) {
        User user = userRepository.findByUsername(username);

        JobApplication jobApplication = new JobApplication();
        jobApplication.setCompanyName(request.getCompanyName());
        jobApplication.setJobTitle(request.getJobTitle());
        jobApplication.setApplicationStatus("APPLIED");
        jobApplication.setAppliedDate(request.getAppliedDate());
        jobApplication.setUser(user);

        JobInformation jobInformation = new JobInformation();
        jobInformation.setJobQualifications(request.getJobQualifications());
        jobInformation.setJobStream(request.getJobStream());
        jobInformation.setJobDescription(request.getJobDescription());
        jobInformation.setJobApplication(jobApplication);
        jobApplication.setJobInformation(jobInformation);

        jobApplicationRepository.save(jobApplication);

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setOldStatus(null);
        statusHistory.setNewStatus("APPLIED");
        statusHistory.setChangedAt(LocalDateTime.now());
        statusHistory.setJobApplication(jobApplication);
        statusHistoryRepository.save(statusHistory);

        notificationService.createNotification(
            username,
            jobApplication.getCompanyName() +
            " → Application created successfully!"
        );

        emailService.sendJobCreatedEmail(
            user.getUserInformation().getUserEmail(),
            username,
            request.getCompanyName()
        );
    }

    public List<JobApplicationResponse> getAllJobsByUser(String username) {
        User user = userRepository.findByUsername(username);
        List<JobApplication> jobs = jobApplicationRepository
                .findByUserId(user.getId());

        List<JobApplicationResponse> response = new ArrayList<>();
        for(JobApplication job : jobs) {
            JobApplicationResponse dto = new JobApplicationResponse();
            dto.setId(job.getId());
            dto.setCompanyName(job.getCompanyName());
            dto.setJobTitle(job.getJobTitle());
            dto.setApplicationStatus(job.getApplicationStatus());
            dto.setAppliedDate(job.getAppliedDate());
            dto.setJobQualifications(
                job.getJobInformation().getJobQualifications());
            dto.setJobStream(
                job.getJobInformation().getJobStream());
            dto.setJobDescription(
                job.getJobInformation().getJobDescription());
            response.add(dto);
        }
        return response;
    }

    public void updateJobStatus(
            String username, Long jobId, String newStatus) {
        JobApplication jobApplication = jobApplicationRepository
                .findById(jobId).orElse(null);

        if(jobApplication == null) return;
        if(!jobApplication.getUser().getUsername().equals(username)) return;

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setOldStatus(jobApplication.getApplicationStatus());
        statusHistory.setNewStatus(newStatus);
        statusHistory.setChangedAt(LocalDateTime.now());
        statusHistory.setJobApplication(jobApplication);
        statusHistoryRepository.save(statusHistory);

        jobApplication.setApplicationStatus(newStatus);
        jobApplicationRepository.save(jobApplication);

        notificationService.createNotification(
            username,
            jobApplication.getCompanyName() +
            " → Status updated to " + newStatus
        );

        emailService.sendStatusUpdateEmail(
            jobApplication.getUser().getUserInformation().getUserEmail(),
            username,
            jobApplication.getCompanyName(),
            newStatus
        );
    }

    public List<JobApplicationResponse> getJobsByStatus(
            String username, String status) {
        User user = userRepository.findByUsername(username);
        List<JobApplication> jobs = jobApplicationRepository
                .findByUserIdAndApplicationStatus(user.getId(), status);

        List<JobApplicationResponse> response = new ArrayList<>();
        for(JobApplication job : jobs) {
            JobApplicationResponse dto = new JobApplicationResponse();
            dto.setId(job.getId());
            dto.setCompanyName(job.getCompanyName());
            dto.setJobTitle(job.getJobTitle());
            dto.setApplicationStatus(job.getApplicationStatus());
            dto.setAppliedDate(job.getAppliedDate());
            dto.setJobQualifications(
                job.getJobInformation().getJobQualifications());
            dto.setJobStream(
                job.getJobInformation().getJobStream());
            dto.setJobDescription(
                job.getJobInformation().getJobDescription());
            response.add(dto);
        }
        return response;
    }

    public void deleteJob(String username, Long jobId) {
        JobApplication jobApplication = jobApplicationRepository
                .findById(jobId).orElse(null);

        if(jobApplication == null) return;
        if(!jobApplication.getUser().getUsername().equals(username)) return;

        notificationService.createNotification(
            username,
            jobApplication.getCompanyName() +
            " → Application deleted!"
        );

        jobApplicationRepository.deleteById(jobId);
    }
}
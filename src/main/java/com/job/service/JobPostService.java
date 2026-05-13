package com.job.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.job.dto.JobPostRequest;
import com.job.dto.JobPostResponse;
import com.job.entity.JobPost;
import com.job.entity.User;
import com.job.repository.JobApplyRepository;
import com.job.repository.JobPostRepository;
import com.job.repository.UserRepository;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private JobApplyRepository jobApplyRepository;

    @Autowired
    private UserRepository userRepository;

    public void createJobPost(String username, JobPostRequest request) {
        User user = userRepository.findByUsername(username);
        JobPost jobPost = new JobPost();
        jobPost.setTitle(request.getTitle());
        jobPost.setDescription(request.getDescription());
        jobPost.setQualifications(request.getQualifications());
        jobPost.setStream(request.getStream());
        jobPost.setCompanyName(request.getCompanyName());
        jobPost.setLocation(request.getLocation());
        jobPost.setJobType(request.getJobType());
        jobPost.setStatus("OPEN");
        jobPost.setPostedDate(LocalDate.now());
        jobPost.setPostedBy(user);
        jobPostRepository.save(jobPost);
    }

    public List<JobPostResponse> getAllOpenJobs() {
        List<JobPost> jobs = jobPostRepository.findByStatus("OPEN");
        return mapToResponse(jobs);
    }

    public List<JobPostResponse> searchJobs(String keyword) {
        List<JobPost> byTitle   = jobPostRepository.findByStatusAndTitleContainingIgnoreCase("OPEN", keyword);
        List<JobPost> byStream  = jobPostRepository.findByStatusAndStreamContainingIgnoreCase("OPEN", keyword);
        List<JobPost> byCompany = jobPostRepository.findByStatusAndCompanyNameContainingIgnoreCase("OPEN", keyword);
        List<JobPost> combined  = new ArrayList<>();
        combined.addAll(byTitle);
        for (JobPost j : byStream)  { if (!combined.contains(j)) combined.add(j); }
        for (JobPost j : byCompany) { if (!combined.contains(j)) combined.add(j); }
        return mapToResponse(combined);
    }

    public List<JobPostResponse> getMyPostedJobs(String username) {
        User user = userRepository.findByUsername(username);
        List<JobPost> jobs = jobPostRepository.findByPostedBy(user);
        return mapToResponse(jobs);
    }

    public void closeJobPost(String username, Long jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId).orElse(null);
        if (jobPost == null) return;
        if (!jobPost.getPostedBy().getUsername().equals(username)) return;
        jobPost.setStatus("CLOSED");
        jobPostRepository.save(jobPost);
    }

    public void deleteJobPost(String username, Long jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId).orElse(null);
        if (jobPost == null) return;
        if (!jobPost.getPostedBy().getUsername().equals(username)) return;
        jobPostRepository.deleteById(jobPostId);
    }

    private List<JobPostResponse> mapToResponse(List<JobPost> jobs) {
        List<JobPostResponse> response = new ArrayList<>();
        for (JobPost job : jobs) {
            JobPostResponse dto = new JobPostResponse();
            dto.setId(job.getId());
            dto.setTitle(job.getTitle());
            dto.setDescription(job.getDescription());
            dto.setQualifications(job.getQualifications());
            dto.setStream(job.getStream());
            dto.setCompanyName(job.getCompanyName());
            dto.setLocation(job.getLocation());
            dto.setJobType(job.getJobType());
            dto.setStatus(job.getStatus());
            dto.setPostedDate(job.getPostedDate());
            dto.setPostedByUsername(job.getPostedBy().getUsername());
            dto.setApplicantCount((int) jobApplyRepository.countByJobPost(job));
            response.add(dto);
        }
        return response;
    }
}
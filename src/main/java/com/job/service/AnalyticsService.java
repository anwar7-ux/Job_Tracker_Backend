package com.job.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.entity.JobApplication;
import com.job.entity.User;
import com.job.repository.JobApplicationRepository;
import com.job.repository.UserRepository;

@Service
public class AnalyticsService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    // GET total applications
    public int getTotalApplications(String username) {
        User user = userRepository.findByUsername(username);
        return jobApplicationRepository
                .findByUserId(user.getId()).size();
    }

    // GET success rate
    public double getSuccessRate(String username) {
        User user = userRepository.findByUsername(username);

        int total = jobApplicationRepository
                .findByUserId(user.getId()).size();

        if(total == 0) return 0;

        int offered = jobApplicationRepository
                .findByUserIdAndApplicationStatus(
                        user.getId(), "OFFERED").size();

        return (double) offered / total * 100;
    }

    // GET applications per status
    public Map<String, Integer> getApplicationsPerStatus(String username) {
        User user = userRepository.findByUsername(username);

        Map<String, Integer> statusCount = new HashMap<>();

        statusCount.put("APPLIED", jobApplicationRepository
                .findByUserIdAndApplicationStatus(
                        user.getId(), "APPLIED").size());

        statusCount.put("INTERVIEW", jobApplicationRepository
                .findByUserIdAndApplicationStatus(
                        user.getId(), "INTERVIEW").size());

        statusCount.put("OFFERED", jobApplicationRepository
                .findByUserIdAndApplicationStatus(
                        user.getId(), "OFFERED").size());

        statusCount.put("REJECTED", jobApplicationRepository
                .findByUserIdAndApplicationStatus(
                        user.getId(), "REJECTED").size());

        return statusCount;
    }

    // GET applications per month
    public Map<String, Integer> getApplicationsPerMonth(String username) {
        User user = userRepository.findByUsername(username);

        List<JobApplication> jobs = jobApplicationRepository
                .findByUserId(user.getId());

        Map<String, Integer> monthCount = new HashMap<>();

        for(JobApplication job : jobs) {
            String month = job.getAppliedDate()
                    .getMonth().toString();
            monthCount.put(month,
                    monthCount.getOrDefault(month, 0) + 1);
        }

        return monthCount;
    }
}
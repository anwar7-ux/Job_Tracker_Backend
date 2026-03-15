package com.job.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.job.service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/total")
    public int getTotalApplications() {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        return analyticsService.getTotalApplications(username);
    }

    @GetMapping("/success-rate")
    public double getSuccessRate() {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        return analyticsService.getSuccessRate(username);
    }

    @GetMapping("/per-status")
    public Map<String, Integer> getApplicationsPerStatus() {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        return analyticsService.getApplicationsPerStatus(username);
    }

    @GetMapping("/per-month")
    public Map<String, Integer> getApplicationsPerMonth() {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        return analyticsService.getApplicationsPerMonth(username);
    }
}
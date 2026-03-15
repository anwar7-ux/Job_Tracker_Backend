package com.job.controller;

import com.job.dto.UserResponse;
import com.job.dto.SiteHeaderRequest;
import com.job.dto.SiteFooterRequest;
import com.job.dto.SiteNavigationRequest;
import com.job.dto.FooterNavigationRequest;
import com.job.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ─── USER MANAGEMENT ───────────────────────────────────────

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PutMapping("/users/{id}/status")
    public String updateUserStatus(
            @PathVariable Long id,
            @RequestParam boolean isActive) {
        return adminService.updateUserStatus(id, isActive);
    }

    // ─── SITE HEADER ───────────────────────────────────────────

    @PostMapping("/site-header")
    public String createHeader(
            @RequestBody SiteHeaderRequest request) {
        return adminService.createHeader(request);
    }

    @PutMapping("/site-header/{id}")
    public String updateHeader(
            @PathVariable Long id,
            @RequestBody SiteHeaderRequest request) {
        return adminService.updateHeader(id, request);
    }

    @DeleteMapping("/site-header/{id}")
    public String deleteHeader(@PathVariable Long id) {
        return adminService.deleteHeader(id);
    }

    // ─── SITE FOOTER ───────────────────────────────────────────

    @PostMapping("/site-footer")
    public String createFooter(
            @RequestBody SiteFooterRequest request) {
        return adminService.createFooter(request);
    }

    @PutMapping("/site-footer/{id}")
    public String updateFooter(
            @PathVariable Long id,
            @RequestBody SiteFooterRequest request) {
        return adminService.updateFooter(id, request);
    }

    @DeleteMapping("/site-footer/{id}")
    public String deleteFooter(@PathVariable Long id) {
        return adminService.deleteFooter(id);
    }

    // ─── SITE NAVIGATION ───────────────────────────────────────

    @PostMapping("/navigation")
    public String createNavigation(
            @RequestBody SiteNavigationRequest request) {
        return adminService.createNavigation(request);
    }

    @PutMapping("/navigation/{id}")
    public String updateNavigation(
            @PathVariable Long id,
            @RequestBody SiteNavigationRequest request) {
        return adminService.updateNavigation(id, request);
    }

    @DeleteMapping("/navigation/{id}")
    public String deleteNavigation(@PathVariable Long id) {
        return adminService.deleteNavigation(id);
    }

    // ─── FOOTER NAVIGATION ─────────────────────────────────────

    @PostMapping("/footer-navigation")
    public String createFooterNavigation(
            @RequestBody FooterNavigationRequest request) {
        return adminService.createFooterNavigation(request);
    }

    @PutMapping("/footer-navigation/{id}")
    public String updateFooterNavigation(
            @PathVariable Long id,
            @RequestBody FooterNavigationRequest request) {
        return adminService.updateFooterNavigation(id, request);
    }

    @DeleteMapping("/footer-navigation/{id}")
    public String deleteFooterNavigation(@PathVariable Long id) {
        return adminService.deleteFooterNavigation(id);
    }
}

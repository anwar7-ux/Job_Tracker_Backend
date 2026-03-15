package com.job.service;

import com.job.dto.UserResponse;
import com.job.dto.SiteHeaderRequest;
import com.job.dto.SiteFooterRequest;
import com.job.dto.SiteNavigationRequest;
import com.job.dto.FooterNavigationRequest;
import com.job.entity.User;
import com.job.entity.SiteHeader;
import com.job.entity.SiteFooter;
import com.job.entity.SiteNavigation;
import com.job.entity.FooterNavigation;
import com.job.repository.UserRepository;
import com.job.repository.SiteHeaderRepository;
import com.job.repository.SiteFooterRepository;
import com.job.repository.SiteNavigationRepository;
import com.job.repository.FooterNavigationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SiteHeaderRepository siteHeaderRepository;

    @Autowired
    private SiteFooterRepository siteFooterRepository;

    @Autowired
    private SiteNavigationRepository siteNavigationRepository;

    @Autowired
    private FooterNavigationRepository footerNavigationRepository;

    // ─── USER MANAGEMENT ───────────────────────────────────────

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> response = new ArrayList<>();
        for (User user : users) {
            UserResponse dto = new UserResponse();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setActive(user.isActive());
            dto.setRole(user.getRole());
            dto.setUserEmail(
                user.getUserInformation().getUserEmail());
            dto.setUserPhone(
                user.getUserInformation().getUserPhone());
            response.add(dto);
        }
        return response;
    }

    public String updateUserStatus(Long id, boolean isActive) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return "User not found!";
        user.setActive(isActive);
        userRepository.save(user);
        return "User status updated!";
    }

    // ─── SITE HEADER ───────────────────────────────────────────

    public String createHeader(SiteHeaderRequest request) {
        SiteHeader header = new SiteHeader();
        header.setWebsiteName(request.getWebsiteName());
        header.setLogoUrl(request.getLogoUrl());
        header.setClassName(request.getClassName());
        siteHeaderRepository.save(header);
        return "Site header created!";
    }

    public String updateHeader(Long id, SiteHeaderRequest request) {
        SiteHeader header = siteHeaderRepository
            .findById(id).orElse(null);
        if (header == null) return "Header not found!";
        header.setWebsiteName(request.getWebsiteName());
        header.setLogoUrl(request.getLogoUrl());
        header.setClassName(request.getClassName());
        siteHeaderRepository.save(header);
        return "Site header updated!";
    }

    public String deleteHeader(Long id) {
        siteHeaderRepository.deleteById(id);
        return "Site header deleted!";
    }

    // ─── SITE FOOTER ───────────────────────────────────────────

    public String createFooter(SiteFooterRequest request) {
        SiteFooter footer = new SiteFooter();
        footer.setAboutText(request.getAboutText());
        footer.setCopyrightText(request.getCopyrightText());
        footer.setContactEmail(request.getContactEmail());
        footer.setClassName(request.getClassName());
        siteFooterRepository.save(footer);
        return "Site footer created!";
    }

    public String updateFooter(Long id, SiteFooterRequest request) {
        SiteFooter footer = siteFooterRepository
            .findById(id).orElse(null);
        if (footer == null) return "Footer not found!";
        footer.setAboutText(request.getAboutText());
        footer.setCopyrightText(request.getCopyrightText());
        footer.setContactEmail(request.getContactEmail());
        footer.setClassName(request.getClassName());
        siteFooterRepository.save(footer);
        return "Site footer updated!";
    }

    public String deleteFooter(Long id) {
        siteFooterRepository.deleteById(id);
        return "Site footer deleted!";
    }

    // ─── SITE NAVIGATION ───────────────────────────────────────

    public String createNavigation(SiteNavigationRequest request) {
        SiteNavigation navigation = new SiteNavigation();
        navigation.setButtonName(request.getButtonName());
        navigation.setSrc(request.getSrc());
        navigation.setClassName(request.getClassName());
        siteNavigationRepository.save(navigation);
        return "Navigation created!";
    }

    public String updateNavigation(
            Long id, SiteNavigationRequest request) {
        SiteNavigation navigation = siteNavigationRepository
            .findById(id).orElse(null);
        if (navigation == null) return "Navigation not found!";
        navigation.setButtonName(request.getButtonName());
        navigation.setSrc(request.getSrc());
        navigation.setClassName(request.getClassName());
        siteNavigationRepository.save(navigation);
        return "Navigation updated!";
    }

    public String deleteNavigation(Long id) {
        siteNavigationRepository.deleteById(id);
        return "Navigation deleted!";
    }

    // ─── FOOTER NAVIGATION ─────────────────────────────────────

    public String createFooterNavigation(
            FooterNavigationRequest request) {
        FooterNavigation footerNav = new FooterNavigation();
        footerNav.setNavigationName(request.getNavigationName());
        footerNav.setNavigationSrc(request.getNavigationSrc());
        footerNav.setClassName(request.getClassName());
        footerNavigationRepository.save(footerNav);
        return "Footer navigation created!";
    }

    public String updateFooterNavigation(
            Long id, FooterNavigationRequest request) {
        FooterNavigation footerNav = footerNavigationRepository
            .findById(id).orElse(null);
        if (footerNav == null) return "Footer navigation not found!";
        footerNav.setNavigationName(request.getNavigationName());
        footerNav.setNavigationSrc(request.getNavigationSrc());
        footerNav.setClassName(request.getClassName());
        footerNavigationRepository.save(footerNav);
        return "Footer navigation updated!";
    }

    public String deleteFooterNavigation(Long id) {
        footerNavigationRepository.deleteById(id);
        return "Footer navigation deleted!";
    }
}
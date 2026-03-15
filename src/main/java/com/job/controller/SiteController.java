package com.job.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.job.entity.FooterNavigation;
import com.job.entity.SiteFooter;
import com.job.entity.SiteHeader;
import com.job.entity.SiteNavigation;
import com.job.service.SiteService;

@RestController
@RequestMapping("/api/site")
public class SiteController {

    @Autowired
    SiteService siteService;

    @GetMapping("/header")
    public List<SiteHeader> getHeader(){
        return siteService.getHeader();
    }

    @GetMapping("/footer")
    public List<SiteFooter> getFooter(){
        return siteService.getFooter();
    }

    @GetMapping("/navigation")
    public List<SiteNavigation> getHeaderNav(){
        return siteService.getNavigation();
    }

    @GetMapping("/footer-navigation")
    public List<FooterNavigation> footerNav(){
        return siteService.getfooterNavigation();
    }
}
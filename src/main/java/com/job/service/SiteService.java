package com.job.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.entity.FooterNavigation;
import com.job.entity.SiteFooter;
import com.job.entity.SiteHeader;
import com.job.entity.SiteNavigation;
import com.job.repository.FooterNavigationRepository;
import com.job.repository.SiteFooterRepository;
import com.job.repository.SiteHeaderRepository;
import com.job.repository.SiteNavigationRepository;

@Service
public class SiteService {

    @Autowired
    SiteHeaderRepository siteHeaderRepository;
    @Autowired
    SiteNavigationRepository siteNavigationRepository;

    @Autowired
    SiteFooterRepository siteFooterRepository;
    @Autowired
    FooterNavigationRepository footerNavigationRepository;

    public List<SiteHeader> getHeader(){
        return  siteHeaderRepository.findAll();

    }

    public List<SiteNavigation> getNavigation(){
        return siteNavigationRepository.findAll();
    }

    public List<SiteFooter> getFooter(){
        return siteFooterRepository.findAll();
    }
    public List<FooterNavigation> getfooterNavigation(){
        return footerNavigationRepository.findAll();
    }

}

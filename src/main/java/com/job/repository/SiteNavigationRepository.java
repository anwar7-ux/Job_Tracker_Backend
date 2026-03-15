package com.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.SiteNavigation;

public interface SiteNavigationRepository extends JpaRepository<SiteNavigation, Long> {
}

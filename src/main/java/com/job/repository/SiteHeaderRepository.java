package com.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.entity.SiteHeader;

public interface SiteHeaderRepository extends JpaRepository<SiteHeader, Long> {
}

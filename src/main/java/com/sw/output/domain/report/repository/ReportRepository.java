package com.sw.output.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sw.output.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

}

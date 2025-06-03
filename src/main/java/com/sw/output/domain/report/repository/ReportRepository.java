package com.sw.output.domain.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sw.output.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r WHERE r.member.id = :memberId AND r.isDeleted = false ORDER BY r.createdAt DESC")
    List<Report> findMyReports(Long memberId);
}

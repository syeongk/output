package com.sw.output.admin.report.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sw.output.domain.report.entity.Report;
import com.sw.output.domain.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminReportService {
    private final ReportRepository reportRepository;

    public List<Report> getReports() {
        return reportRepository.findAll();
    }
}

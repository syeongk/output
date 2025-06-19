package com.sw.output.admin.report.controller;

import com.sw.output.admin.report.service.AdminFeedbackService;
import com.sw.output.admin.report.service.AdminReportService;
import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/reports")
public class AdminReportController {
    private final AdminReportService adminReportService;
    private final AdminFeedbackService adminFeedbackService;

    @GetMapping
    public String getReports(Model model) {
        List<Report> reports = adminReportService.getReports();
        model.addAttribute("reports", reports);
        return "admin/report/report-list";
    }

    @GetMapping("/{reportId}")
    public String getReportDetail(@PathVariable Long reportId, Model model) {
        Feedback feedback = adminFeedbackService.getFeedback(reportId);
        model.addAttribute("feedback", feedback);
        return "admin/report/report-detail";
    }


}

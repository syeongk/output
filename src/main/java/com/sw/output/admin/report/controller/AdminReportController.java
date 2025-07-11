package com.sw.output.admin.report.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sw.output.admin.report.service.AdminFeedbackService;
import com.sw.output.admin.report.service.AdminReportService;
import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.Report;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/reports")
public class AdminReportController {
    private final AdminReportService adminReportService;
    private final AdminFeedbackService adminFeedbackService;

    @GetMapping
    public String getReports(Model model, @RequestParam(required = false) Long memberId, @RequestParam(required = false) Long interviewSetId) {
        List<Report> reports;
        if (memberId != null) {
            reports = adminReportService.getReportsByMemberId(memberId);
        } else if (interviewSetId != null) {
            reports = adminReportService.getReportsByInterviewSetId(interviewSetId);
        } else {
            reports = adminReportService.getReports();
        }
        model.addAttribute("reports", reports);
        return "admin/report/report-list";
    }

    @GetMapping("/{reportId}")
    public String getReportDetail(@PathVariable Long reportId, Model model) {
        List<Feedback> feedbacks = adminFeedbackService.getFeedback(reportId);
        model.addAttribute("feedbacks", feedbacks);
        return "admin/report/report-detail";
    }


}

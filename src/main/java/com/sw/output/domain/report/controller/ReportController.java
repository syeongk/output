package com.sw.output.domain.report.controller;

import com.sw.output.domain.report.dto.ReportRequestDTO;
import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.domain.report.service.ReportService;
import com.sw.output.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("{reportId}/ai-feedback")
    public ApiResponse<Void> createAiFeedback(@PathVariable Long reportId, @RequestBody @Valid ReportRequestDTO.CreateAiFeedbackDTO request) {
        reportService.createAIFeedback(reportId, request);
        return ApiResponse.success();
    }

    @GetMapping("{reportId}")
    public ApiResponse<List<ReportResponseDTO.GetReportDetailDTO>> getReport(@PathVariable Long reportId) {
        List<ReportResponseDTO.GetReportDetailDTO> response = reportService.getReport(reportId);
        return ApiResponse.success(response);
    }

    @DeleteMapping("{reportId}")
    public ApiResponse<Void> deleteReport(@PathVariable Long reportId) {
        reportService.deleteReport(reportId);
        return ApiResponse.success();
    }
}

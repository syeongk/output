package com.sw.output.domain.report.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sw.output.domain.report.dto.FeedbackResponseDTO;
import com.sw.output.domain.report.dto.ReportRequestDTO;
import com.sw.output.domain.report.dto.ValidationResultDTO;
import com.sw.output.domain.report.service.ReportService;
import com.sw.output.global.response.ApiResponse;
import com.sw.output.global.response.successcode.CommonSuccessCode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("{reportId}/ai-feedback")
    public ApiResponse<Void> createAiFeedback(@PathVariable Long reportId, @RequestBody @Valid ReportRequestDTO.CreateAiFeedbackDTO request) {
        ValidationResultDTO.AiFeedbackValidationDTO validationResultDTO = reportService.validateAIFeedback(reportId, request);
        reportService.createAIFeedback(reportId, request, validationResultDTO);
        return ApiResponse.success(CommonSuccessCode.ACCEPTED, null);
    }

    @GetMapping("{reportId}")
    public ApiResponse<FeedbackResponseDTO.FeedbacksDTO> getReport(
            @PathVariable Long reportId,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(defaultValue = "20") int pageSize) {
        FeedbackResponseDTO.FeedbacksDTO response = reportService.getReport(reportId, cursorId, cursorCreatedAt, pageSize);
        return ApiResponse.success(response);
    }

    @DeleteMapping("{reportId}")
    public ApiResponse<Void> deleteReport(@PathVariable Long reportId) {
        reportService.deleteReport(reportId);
        return ApiResponse.success();
    }
}

package com.sw.output.domain.report.controller;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "결과 레포트")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("{reportId}/ai-feedback")
    @Operation(summary = "AI 피드백 생성 API", description = "비동기 처리, 레포트 상세 조회 시 피드백 조회 가능")
    public ApiResponse<Void> createAiFeedback(@PathVariable Long reportId, @RequestBody @Valid ReportRequestDTO.CreateAiFeedbackDTO request) {
        ValidationResultDTO.AiFeedbackValidationDTO validationResultDTO = reportService.validateAIFeedback(reportId, request);
        reportService.createAIFeedback(reportId, request, validationResultDTO);
        return ApiResponse.success(CommonSuccessCode.ACCEPTED, null);
    }

    @GetMapping("{reportId}")
    @Operation(summary = "[개발중] 결과 레포트 상세 조회 API", description = "결과 레포트 내용인 질문, 사용자 답변, AI 피드백 목록 조회")
    public ApiResponse<FeedbackResponseDTO.FeedbacksDTO> getReport(
            @PathVariable Long reportId,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") int pageSize) {
        FeedbackResponseDTO.FeedbacksDTO response = reportService.getReport(reportId, cursorId, pageSize);
        return ApiResponse.success(response);
    }

    @DeleteMapping("{reportId}")
    @Operation(summary = "결과 레포트 삭제 API", description = "결과 레포트 삭제 시 soft delete 적용")
    public ApiResponse<Void> deleteReport(@PathVariable Long reportId) {
        reportService.deleteReport(reportId);
        return ApiResponse.success();
    }
}

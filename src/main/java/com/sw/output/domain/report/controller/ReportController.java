package com.sw.output.domain.report.controller;

import com.sw.output.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    @PostMapping("{reportId}/ai-feedback")
    public ApiResponse<Void> createAiFeedback(@PathVariable Long reportId) {
        return ApiResponse.success();
    }

    @GetMapping("{reportId}")
    public ApiResponse<Void> getReport(@PathVariable Long reportId) {
        return ApiResponse.success();
    }
}

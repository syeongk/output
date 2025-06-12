package com.sw.output.domain.complaint.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sw.output.domain.complaint.dto.ComplaintRequestDTO;
import com.sw.output.domain.complaint.service.ComplaintService;
import com.sw.output.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/complaints")
@Tag(name = "신고")
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complainService;

    @PostMapping("/interview-sets/{interviewSetId}")
    @Operation(summary = "면접 세트 신고 API", description = "")
    public ApiResponse<Void> createComplaint(@RequestBody @Valid ComplaintRequestDTO.CreateComplaintDTO request,
            @PathVariable Long interviewSetId) {
        complainService.createComplaint(request, interviewSetId);
        return ApiResponse.success();
    }

}

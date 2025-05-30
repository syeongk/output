package com.sw.output.domain.complaint.controller;

import com.sw.output.domain.complaint.dto.ComplaintRequestDTO;
import com.sw.output.domain.complaint.service.ComplaintService;
import com.sw.output.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/complaints")
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complainService;

    @PostMapping("/interview-sets/{interviewSetId}")
    public ApiResponse<Void> createComplaint(@RequestBody @Valid ComplaintRequestDTO.CreateComplaintDTO request,
            @PathVariable Long interviewSetId) {
        complainService.createComplaint(request, interviewSetId);
        return ApiResponse.success();
    }

}

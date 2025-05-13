package com.sw.output.domain.interviewset.controller;

import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.service.InterviewSetService;
import com.sw.output.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview-sets")
@RequiredArgsConstructor
public class InterviewSetController {
    private final InterviewSetService interviewSetService;

    @GetMapping("")
    public ApiResponse<Void> getInterviewSets() {
        return ApiResponse.success();
    }

    @GetMapping("{interviewSetId}")
    public ApiResponse<InterviewSetResponseDTO.GetInterviewSetDTO> getInterviewSet(@PathVariable Long interviewSetId) {
        InterviewSetResponseDTO.GetInterviewSetDTO getInterviewSetDTO = interviewSetService.getInterviewSet(interviewSetId);
        return ApiResponse.success(getInterviewSetDTO);
    }

    @PostMapping("")
    public ApiResponse<InterviewSetResponseDTO.CreateInterviewSetDTO> createInterviewSet(@RequestBody @Valid InterviewSetRequestDTO.CreateInterviewSetDTO request) {
        InterviewSetResponseDTO.CreateInterviewSetDTO response = interviewSetService.createInterviewSet(request);
        return ApiResponse.success(response);
    }

    @PatchMapping("{interviewSetId}")
    public ApiResponse<Void> updateInterviewSet(@PathVariable Long interviewSetId) {
        return ApiResponse.success();
    }

    @DeleteMapping("{interviewSetId}")
    public ApiResponse<Void> deleteInterviewSet(@PathVariable Long interviewSetId) {
        interviewSetService.deleteInterviewSet(interviewSetId);
        return ApiResponse.success();
    }

    @PostMapping("ai-question-answers")
    public ApiResponse<Void> createAiQuestionAnswers() {
        return ApiResponse.success();
    }

    @PostMapping("{interviewSetId}/bookmarks")
    public ApiResponse<Void> createBookmark(@PathVariable Long interviewSetId) {
        return ApiResponse.success();
    }

    @DeleteMapping("{interviewSetId}/bookmarks")
    public ApiResponse<Void> deleteBookmark(@PathVariable Long interviewSetId) {
        return ApiResponse.success();
    }

    @PostMapping("{interviewSetId}/reports")
    public ApiResponse<Void> startInterview(@PathVariable Long interviewSetId) {
        return ApiResponse.success();
    }
}

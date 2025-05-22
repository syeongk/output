package com.sw.output.domain.interviewset.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.service.BookmarkService;
import com.sw.output.domain.interviewset.service.InterviewSetService;
import com.sw.output.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/interview-sets")
@RequiredArgsConstructor
public class InterviewSetController {
    private final InterviewSetService interviewSetService;
    private final BookmarkService bookmarkService;

    @GetMapping("")
    public ApiResponse<String> getInterviewSets(
            @RequestParam(required = false) String jobCategories,
            @RequestParam(required = false) String interviewSetCategories,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "0") int cursor) {
        // interviewSetService.getInterviewSets(jobCategories, interviewSetCategories,
        // keyword, size, cursor);
        return ApiResponse.success();
    }

    @GetMapping("{interviewSetId}")
    public ApiResponse<InterviewSetResponseDTO.GetInterviewSetDTO> getInterviewSet(@PathVariable Long interviewSetId) {
        InterviewSetResponseDTO.GetInterviewSetDTO getInterviewSetDTO = interviewSetService
                .getInterviewSet(interviewSetId);
        return ApiResponse.success(getInterviewSetDTO);
    }

    @PostMapping("")
    public ApiResponse<InterviewSetResponseDTO.CreateInterviewSetDTO> createInterviewSet(
            @RequestBody @Valid InterviewSetRequestDTO.CreateInterviewSetDTO request) {
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
        bookmarkService.createBookmark(interviewSetId);
        return ApiResponse.success();
    }

    @DeleteMapping("{interviewSetId}/bookmarks")
    public ApiResponse<Void> deleteBookmark(@PathVariable Long interviewSetId) {
        bookmarkService.deleteBookmark(interviewSetId);
        return ApiResponse.success();
    }

    @PostMapping("{interviewSetId}/reports")
    public ApiResponse<Void> startInterview(@PathVariable Long interviewSetId) {
        return ApiResponse.success();
    }
}

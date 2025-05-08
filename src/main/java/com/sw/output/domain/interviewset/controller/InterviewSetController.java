package com.sw.output.domain.interviewset.controller;

import com.sw.output.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview-sets")
@RequiredArgsConstructor
public class InterviewSetController {

    @GetMapping("")
    public ApiResponse<Void> getInterviewSets() {
        return ApiResponse.success();
    }

    @GetMapping("{interviewSetId}")
    public ApiResponse<Void> getInterviewSet(@PathVariable Long interviewSetId) {
        return ApiResponse.success();
    }

    @PostMapping("")
    public ApiResponse<Void> createInterviewSet() {
        return ApiResponse.success();
    }

    @PatchMapping("{interviewSetId}")
    public ApiResponse<Void> updateInterviewSet(@PathVariable Long interviewSetId) {
        return ApiResponse.success();
    }

    @DeleteMapping("{interviewSetId}")
    public ApiResponse<Void> deleteInterviewSet(@PathVariable Long interviewSetId) {
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

package com.sw.output.domain.interviewset.controller;

import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.entity.InterviewCategory;
import com.sw.output.domain.interviewset.entity.InterviewSetSortType;
import com.sw.output.domain.interviewset.entity.JobCategory;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.interviewset.service.BookmarkService;
import com.sw.output.domain.interviewset.service.InterviewSetService;
import com.sw.output.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interview-sets")
@RequiredArgsConstructor
public class InterviewSetController {
    private final InterviewSetService interviewSetService;
    private final BookmarkService bookmarkService;

    @GetMapping("")
    public ApiResponse<List<InterviewSetSummaryProjection>> getInterviewSets(
            @RequestParam(required = false) JobCategory jobCategory,
            @RequestParam(required = false) InterviewCategory interviewCategory,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) InterviewSetSortType sortType,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "0") int cursor) {
        List<InterviewSetSummaryProjection> interviewSets = interviewSetService.getInterviewSets(jobCategory,
                interviewCategory, keyword, sortType, size, cursor);
        return ApiResponse.success(interviewSets);
    }

    @GetMapping("{interviewSetId}")
    public ApiResponse<InterviewSetResponseDTO.GetInterviewSetDTO> getInterviewSet(@PathVariable Long interviewSetId) {
        InterviewSetResponseDTO.GetInterviewSetDTO getInterviewSetDTO = interviewSetService
                .getInterviewSet(interviewSetId);
        return ApiResponse.success(getInterviewSetDTO);
    }

    @PostMapping("")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetIdDTO> createInterviewSet(
            @RequestBody @Valid InterviewSetRequestDTO.InterviewSetDTO request) {
        InterviewSetResponseDTO.InterviewSetIdDTO response = interviewSetService.createInterviewSet(request);
        return ApiResponse.success(response);
    }

    @PutMapping("{interviewSetId}")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetIdDTO> updateInterviewSet(
            @PathVariable Long interviewSetId,
            @RequestBody @Valid InterviewSetRequestDTO.InterviewSetDTO request) {
        InterviewSetResponseDTO.InterviewSetIdDTO response = interviewSetService.updateInterviewSet(interviewSetId,
                request);
        return ApiResponse.success(response);
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

    @PostMapping("{interviewSetId}/duplicate")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetIdDTO> duplicateInterviewSet(
            @PathVariable Long interviewSetId,
            @RequestBody @Valid InterviewSetRequestDTO.InterviewSetDTO request) {
        InterviewSetResponseDTO.InterviewSetIdDTO response = interviewSetService
                .duplicateInterviewSet(interviewSetId, request);
        return ApiResponse.success(response);
    }
}

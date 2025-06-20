package com.sw.output.domain.interviewset.controller;

import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.entity.InterviewCategory;
import com.sw.output.domain.interviewset.entity.InterviewSetSortType;
import com.sw.output.domain.interviewset.entity.JobCategory;
import com.sw.output.domain.interviewset.entity.QuestionAnswerSortType;
import com.sw.output.domain.interviewset.service.BookmarkService;
import com.sw.output.domain.interviewset.service.InterviewSetService;
import com.sw.output.global.dto.CommonResponseDTO;
import com.sw.output.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview-sets")
@RequiredArgsConstructor
@Tag(name = "면접 세트")
public class InterviewSetController {
    private final InterviewSetService interviewSetService;
    private final BookmarkService bookmarkService;

    @GetMapping("")
    @Operation(summary = "면접 세트 목록 조회 API", description = "직무 카테고리, 면접 카테고리, 키워드로 필터링 선택 후 면접 세트 조회")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetsCursorDTO> getInterviewSets(
            @RequestParam(required = false) JobCategory jobCategory,
            @RequestParam(required = false) InterviewCategory interviewCategory,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) InterviewSetSortType sortType,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) Long cursorId
    ) {
        InterviewSetResponseDTO.InterviewSetsCursorDTO interviewSets = interviewSetService.getInterviewSets(
                jobCategory,
                interviewCategory,
                keyword,
                sortType,
                pageSize,
                cursorId
        );
        return ApiResponse.success(interviewSets);
    }

    @GetMapping("{interviewSetId}")
    @Operation(summary = "면접 세트 상세 조회 API", description = "면접 세트 정보 및 질문 답변 목록 조회")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetCursorDTO> getInterviewSet(
            @PathVariable Long interviewSetId,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "CREATED_AT") QuestionAnswerSortType questionAnswerSortType
    ) {
        InterviewSetResponseDTO.InterviewSetCursorDTO response = interviewSetService.getInterviewSet(
                interviewSetId,
                cursorId,
                pageSize,
                questionAnswerSortType
        );
        return ApiResponse.success(response);
    }

    @PostMapping("")
    @Operation(summary = "면접 세트 생성 API", description = "")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetIdDTO> createInterviewSet(
            @RequestBody @Valid InterviewSetRequestDTO.InterviewSetDTO request) {
        InterviewSetResponseDTO.InterviewSetIdDTO response = interviewSetService.createInterviewSet(request);
        return ApiResponse.success(response);
    }

    @PutMapping("{interviewSetId}")
    @Operation(summary = "면접 세트 수정 API", description = "")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetIdDTO> updateInterviewSet(
            @PathVariable Long interviewSetId,
            @RequestBody @Valid InterviewSetRequestDTO.InterviewSetDTO request) {
        InterviewSetResponseDTO.InterviewSetIdDTO response = interviewSetService.updateInterviewSet(interviewSetId,
                request);
        return ApiResponse.success(response);
    }

    @DeleteMapping("{interviewSetId}")
    @Operation(summary = "면접 세트 삭제 API", description = "면접 세트 삭제 시 soft delete 적용")
    public ApiResponse<Void> deleteInterviewSet(@PathVariable Long interviewSetId) {
        interviewSetService.deleteInterviewSet(interviewSetId);
        return ApiResponse.success();
    }

    @PostMapping("ai-questions")
    @Operation(summary = "AI 질문 생성 API", description = "면접 카테고리, 직무 카테고리, 면접 세트 제목에 대한 AI 질문 생성")
    public ApiResponse<InterviewSetResponseDTO.GetQuestionsDTO> createAiQuestions(
            @RequestBody @Valid InterviewSetRequestDTO.CreateQuestionsPromptDTO request) {
        InterviewSetResponseDTO.GetQuestionsDTO response = interviewSetService.createAiQuestions(request);
        return ApiResponse.success(response);
    }

    @PostMapping("{interviewSetId}/bookmarks")
    @Operation(summary = "북마크 생성 API", description = "")
    public ApiResponse<Void> createBookmark(@PathVariable Long interviewSetId) {
        bookmarkService.createBookmark(interviewSetId);
        return ApiResponse.success();
    }

    @DeleteMapping("{interviewSetId}/bookmarks")
    @Operation(summary = "북마크 삭제 API", description = "북마크 삭제 시 soft delete 적용")
    public ApiResponse<Void> deleteBookmark(@PathVariable Long interviewSetId) {
        bookmarkService.deleteBookmark(interviewSetId);
        return ApiResponse.success();
    }

    @PostMapping("{interviewSetId}/reports")
    @Operation(summary = "모의 면접 시작 API", description = "모의 면접 시작 버튼 탭 시 모의 면접 시작(Report 생성)")
    public ApiResponse<CommonResponseDTO.IdResponseDTO> startInterview(@PathVariable Long interviewSetId) {
        CommonResponseDTO.IdResponseDTO response = interviewSetService.startMockInterview(interviewSetId);
        return ApiResponse.success(response);
    }

    @PostMapping("{interviewSetId}/duplicate")
    @Operation(summary = "면접 세트 복제 API", description = "면접 세트 복제 편집 화면에서 저장 시 복제")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetIdDTO> duplicateInterviewSet(
            @PathVariable Long interviewSetId,
            @RequestBody @Valid InterviewSetRequestDTO.InterviewSetDTO request) {
        InterviewSetResponseDTO.InterviewSetIdDTO response = interviewSetService
                .duplicateInterviewSet(interviewSetId, request);
        return ApiResponse.success(response);
    }
}

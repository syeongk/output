package com.sw.output.domain.member.controller;

import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.member.dto.MemberRequestDTO;
import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.dto.MyPageResponseDTO;
import com.sw.output.domain.member.service.MemberService;
import com.sw.output.domain.member.service.MyPageService;
import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.domain.report.service.ReportService;
import com.sw.output.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class MemberController {
    private final MyPageService myPageService;
    private final MemberService memberService;
    private final ReportService reportService;

    @GetMapping("")
    public ApiResponse<MemberResponseDTO.GetMyPageDTO> getMyPage() {
        MemberResponseDTO.GetMyPageDTO response = myPageService.getMyPage();
        return ApiResponse.success(response);
    }

    @GetMapping("/interview-sets/bookmarks")
    public ApiResponse<List<InterviewSetSummaryProjection>> getMyBookmarks(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        List<InterviewSetSummaryProjection> response = myPageService.getMyBookmarkedInterviewSets(cursorId, cursorCreatedAt, pageSize);
        return ApiResponse.success(response);
    }

    @GetMapping("/interview-sets")
    public ApiResponse<MyPageResponseDTO.GetMyInterviewSetsDTO> getMyInterviewSets(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(defaultValue = "20") int pageSize
    ) {

        MyPageResponseDTO.GetMyInterviewSetsDTO response = myPageService.getMyInterviewSets(cursorId, cursorCreatedAt, pageSize);
        return ApiResponse.success(response);
    }

    @GetMapping("/reports")
    public ApiResponse<List<ReportResponseDTO.GetReportDTO>> getMyReports(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        List<ReportResponseDTO.GetReportDTO> response = myPageService.getMyReports(cursorId, cursorCreatedAt, pageSize);
        return ApiResponse.success(response);
    }

    @PatchMapping("/nickname")
    public ApiResponse<Void> updateNickname(@RequestBody @Valid MemberRequestDTO.UpdateNicknameDTO request) {
        memberService.updateNickname(request);
        return ApiResponse.success();
    }

    @DeleteMapping("")
    public ApiResponse<Void> deleteMember() {
        memberService.deleteMember();
        return ApiResponse.success();
    }
}

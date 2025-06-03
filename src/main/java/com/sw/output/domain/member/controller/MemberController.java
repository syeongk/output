package com.sw.output.domain.member.controller;

import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.member.dto.MemberRequestDTO;
import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.service.MemberService;
import com.sw.output.domain.member.service.MyPageService;
import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.domain.report.service.ReportService;
import com.sw.output.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<List<InterviewSetSummaryProjection>> getMyBookmarks() {
        List<InterviewSetSummaryProjection> response = myPageService.getMyBookmarkedInterviewSets();
        return ApiResponse.success(response);
    }

    @GetMapping("/interview-sets")
    public ApiResponse<List<InterviewSetSummaryProjection>> getMyInterviewSets() {
        List<InterviewSetSummaryProjection> response = myPageService.getMyInterviewSets();
        return ApiResponse.success(response);
    }

    @GetMapping("/reports")
    public ApiResponse<List<ReportResponseDTO.GetReportDTO>> getMyReports() {
        List<ReportResponseDTO.GetReportDTO> response = myPageService.getMyReports();
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

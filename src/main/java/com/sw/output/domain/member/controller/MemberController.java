package com.sw.output.domain.member.controller;

import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.member.dto.MemberRequestDTO;
import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.service.MemberService;
import com.sw.output.domain.member.service.MyPageService;
import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class MemberController {
    private final MyPageService myPageService;
    private final MemberService memberService;

    @GetMapping("")
    public ApiResponse<MemberResponseDTO.GetMyPageDTO> getMyPage() {
        MemberResponseDTO.GetMyPageDTO response = myPageService.getMyPage();
        return ApiResponse.success(response);
    }

    @GetMapping("/interview-sets/bookmarks")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetsCursorDTO> getMyBookmarks(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        InterviewSetResponseDTO.InterviewSetsCursorDTO response = myPageService.getMyBookmarkedInterviewSets(cursorId, cursorCreatedAt, pageSize);
        return ApiResponse.success(response);
    }

    @GetMapping("/interview-sets")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetsCursorDTO> getMyInterviewSets(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(defaultValue = "20") int pageSize
    ) {

        InterviewSetResponseDTO.InterviewSetsCursorDTO response = myPageService.getMyInterviewSets(cursorId, cursorCreatedAt, pageSize);
        return ApiResponse.success(response);
    }

    @GetMapping("/reports")
    public ApiResponse<ReportResponseDTO.ReportsDTO> getMyReports(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        ReportResponseDTO.ReportsDTO response = myPageService.getMyReports(cursorId, cursorCreatedAt, pageSize);
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

package com.sw.output.domain.member.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.member.dto.MemberRequestDTO;
import com.sw.output.domain.member.service.MyPageService;
import com.sw.output.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class MemberController {
    private final MyPageService myPageService;

    @GetMapping("")
    public ApiResponse<Void> getMyPage() {
        return ApiResponse.success();
    }

    @GetMapping("/interview-sets/bookmarks")
    public ApiResponse<List<InterviewSetSummaryProjection>> getMyBookmarks() {
        List<InterviewSetSummaryProjection> response = myPageService.getBookmarkedInterviewSets();
        return ApiResponse.success(response);
    }

    @GetMapping("/interview-sets")
    public ApiResponse<Void> getMyInterviewSets() {
        return ApiResponse.success();
    }

    @GetMapping("/reports")
    public ApiResponse<Void> getMyReports() {
        return ApiResponse.success();
    }

    @PatchMapping("/nickname")
    public ApiResponse<Void> updateNickname(@RequestBody @Valid MemberRequestDTO.UpdateNicknameDTO request) {
        myPageService.updateNickname(request);
        return ApiResponse.success();
    }
}

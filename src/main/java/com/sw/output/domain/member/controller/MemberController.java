package com.sw.output.domain.member.controller;

import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.member.service.MyPageService;
import com.sw.output.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {
    private final MyPageService myPageService;

    @GetMapping("/me")
    public ApiResponse<Void> getMyPage() {
        return ApiResponse.success();
    }

    @GetMapping("/me/interview-sets/bookmarks")
    public ApiResponse<List<InterviewSetSummaryProjection>> getMyBookmarks() {
        List<InterviewSetSummaryProjection> response = myPageService.getBookmarkedInterviewSets();
        return ApiResponse.success(response);
    }

    @GetMapping("/me/interview-sets")
    public ApiResponse<Void> getMyInterviewSets() {
        return ApiResponse.success();
    }

    @GetMapping("/me/reports")
    public ApiResponse<Void> getMyReports() {
        return ApiResponse.success();
    }
}

package com.sw.output.domain.member.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.member.dto.MemberRequestDTO;
import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.service.MemberService;
import com.sw.output.domain.member.service.MyPageService;
import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
@Tag(name = "마이페이지")
public class MemberController {
    private final MyPageService myPageService;
    private final MemberService memberService;

    @GetMapping("")
    @Operation(summary = "마이페이지 조회 API", description = "닉네임, 프로필 이미지 조회")
    public ApiResponse<MemberResponseDTO.GetMyPageDTO> getMyPage() {
        MemberResponseDTO.GetMyPageDTO response = myPageService.getMyPage();
        return ApiResponse.success(response);
    }

    @GetMapping("/interview-sets/bookmarks")
    @Operation(summary = "북마크 목록 조회 API", description = "북마크 생성일 기준 내림차순 조회")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetsCursorDTO> getMyBookmarks(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") @Min(value = 1, message = "페이지 크기는 1 이상입니다") int pageSize
    ) {
        InterviewSetResponseDTO.InterviewSetsCursorDTO response = myPageService.getMyBookmarkedInterviewSets(cursorId, pageSize);
        return ApiResponse.success(response);
    }

    @GetMapping("/interview-sets")
    @Operation(summary = "면접 세트 목록 조회 API", description = "면접 세트 생성일 기준 내림차순 조회")
    public ApiResponse<InterviewSetResponseDTO.InterviewSetsCursorDTO> getMyInterviewSets(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") @Min(value = 1, message = "페이지 크기는 1 이상입니다") int pageSize 
    ) {

        InterviewSetResponseDTO.InterviewSetsCursorDTO response = myPageService.getMyInterviewSets(cursorId, pageSize);
        return ApiResponse.success(response);
    }

    @GetMapping("/reports")
    @Operation(summary = "결과 레포트 목록 조회 API", description = "결과 레포트 생성일 기준 내림차순 조회")
    public ApiResponse<ReportResponseDTO.ReportsDTO> getMyReports(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") @Min(value = 1, message = "페이지 크기는 1 이상입니다") int pageSize
    ) {
        ReportResponseDTO.ReportsDTO response = myPageService.getMyReports(cursorId, pageSize);
        return ApiResponse.success(response);
    }

    @PatchMapping("/nickname")
    @Operation(summary = "닉네임 수정 API", description = "이미 존재하는 닉네임인 경우 수정 불가")
    public ApiResponse<Void> updateNickname(@RequestBody @Valid MemberRequestDTO.UpdateNicknameDTO request) {
        memberService.updateNickname(request);
        return ApiResponse.success();
    }

    @DeleteMapping("")
    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴 시 soft delete 적용")
    public ApiResponse<Void> deleteMember() {
        memberService.deleteMember();
        return ApiResponse.success();
    }
}

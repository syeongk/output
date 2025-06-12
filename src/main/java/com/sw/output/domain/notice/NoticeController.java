package com.sw.output.domain.notice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sw.output.domain.notice.dto.NoticeResponseDTO;
import com.sw.output.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
@Tag(name = "공지사항")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    @Operation(summary = "공지사항 목록 조회 API", description = "공지사항 생성일 기준 내림차순 조회")
    public ApiResponse<NoticeResponseDTO.NoticesDTO> getNotices(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        NoticeResponseDTO.NoticesDTO notices = noticeService.getNotices(cursorId, pageSize);
        return ApiResponse.success(notices);
    }

    @GetMapping("{noticeId}")
    @Operation(summary = "공지사항 상세 조회 API", description = "")
    public ApiResponse<NoticeResponseDTO.NoticeDetailDTO> getNotice(@PathVariable Long noticeId) {
        NoticeResponseDTO.NoticeDetailDTO notice = noticeService.getNotice(noticeId);
        return ApiResponse.success(notice);
    }
}

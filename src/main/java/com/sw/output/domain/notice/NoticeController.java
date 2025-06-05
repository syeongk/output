package com.sw.output.domain.notice;

import com.sw.output.domain.notice.dto.NoticeResponseDTO;
import com.sw.output.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    public ApiResponse<NoticeResponseDTO.NoticesDTO> getNotices(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        NoticeResponseDTO.NoticesDTO notices = noticeService.getNotices(cursorId, cursorCreatedAt, pageSize);
        return ApiResponse.success(notices);
    }

    @GetMapping("{noticeId}")
    public ApiResponse<NoticeResponseDTO.NoticeDetailDTO> getNotice(@PathVariable Long noticeId) {
        NoticeResponseDTO.NoticeDetailDTO notice = noticeService.getNotice(noticeId);
        return ApiResponse.success(notice);
    }
}

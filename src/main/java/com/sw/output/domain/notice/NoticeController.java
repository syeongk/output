package com.sw.output.domain.notice;

import com.sw.output.domain.notice.dto.NoticeResponseDTO;
import com.sw.output.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    public ApiResponse<List<NoticeResponseDTO.NoticeDTO>> getNotices() {
        List<NoticeResponseDTO.NoticeDTO> notices = noticeService.getNotices();
        return ApiResponse.success(notices);
    }

    @GetMapping("{noticeId}")
    public ApiResponse<NoticeResponseDTO.NoticeDetailDTO> getNotice(@PathVariable Long noticeId) {
        NoticeResponseDTO.NoticeDetailDTO notice = noticeService.getNotice(noticeId);
        return ApiResponse.success(notice);
    }
}

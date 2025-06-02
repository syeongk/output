package com.sw.output.domain.notice;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sw.output.domain.notice.dto.NoticeRequestDTO;
import com.sw.output.domain.notice.entity.Notice;
import com.sw.output.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    public ApiResponse<List<Notice>> getNotices() {
        noticeService.getNotices();
        return ApiResponse.success();
    }

    @GetMapping("{noticeId}")
    public ApiResponse<Notice> getNotice(@PathVariable Long noticeId) {
        noticeService.getNotice(noticeId);
        return ApiResponse.success();
    }

    @PostMapping
    public ApiResponse<Notice> createNotice(@RequestBody @Valid NoticeRequestDTO.NoticeDTO request) {
        noticeService.createNotice(request);
        return ApiResponse.success();
    }

    @PutMapping("{noticeId}")
    public ApiResponse<Notice> updateNotice(@PathVariable Long noticeId, @RequestBody @Valid NoticeRequestDTO.NoticeDTO request) {
        noticeService.updateNotice(noticeId, request);
        return ApiResponse.success();
    }

    @DeleteMapping("{noticeId}")
    public ApiResponse<Void> deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return ApiResponse.success();
    }
}

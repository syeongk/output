package com.sw.output.admin.notice.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class AdminNoticeResponseDTO {
    @Getter
    @Builder
    public static class NoticeDTO {
        private Long id;
        private String title;
        private LocalDateTime createdAt;
    }
}

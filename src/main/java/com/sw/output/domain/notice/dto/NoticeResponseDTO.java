package com.sw.output.domain.notice.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class NoticeResponseDTO {
    @Getter
    @Builder
    public static class NoticeDTO {
        private Long id;
        private String title;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class NoticeDetailDTO {
        private String title;
        private String content;
        private LocalDateTime createdAt;
    }
}

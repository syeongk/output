package com.sw.output.domain.notice.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

public class NoticeResponseDTO {
    @Getter
    @Builder
    public static class NoticeDTO {
        private Long id;
        private String title;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class NoticeDetailDTO {
        private String title;
        private String content;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class NoticeCursorDTO {
        private Long id;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class NoticesDTO {
        private List<NoticeDTO> notices;
        private NoticeCursorDTO nextCursor;
    }
}

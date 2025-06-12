package com.sw.output.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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
    public static class NoticesDTO {
        private List<NoticeDTO> notices;
        private Long nextCursor;
    }
}

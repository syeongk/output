package com.sw.output.global.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class CommonResponseDTO {
    @Getter
    @Builder
    public static class IdResponseDTO {
        private Long id;
    }

    @Getter
    @Builder
    public static class CursorDTO {
        private Long id;
        private LocalDateTime createdAt;
    }
}

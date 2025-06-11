package com.sw.output.global.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

public class CommonResponseDTO {
    @Getter
    @Builder
    public static class IdResponseDTO {
        private Long id;
    }

    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CursorDTO {
        private Long id;
        private LocalDateTime createdAt;
        private String title;
        private Integer bookmarkCount;
        private Integer mockCount;
    }
}

package com.sw.output.global.dto;

import lombok.Builder;
import lombok.Getter;

public class CommonResponseDTO {
    @Getter
    @Builder
    public static class IdResponseDTO {
        private Long id;
    }
}

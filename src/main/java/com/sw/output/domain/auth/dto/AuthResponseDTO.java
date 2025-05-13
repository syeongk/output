package com.sw.output.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

public class AuthResponseDTO {
    @Getter
    @Builder
    public static class TokenDTO {
        private String accessToken;
        private String refreshToken;
    }
}

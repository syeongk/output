package com.sw.output.domain.auth.converter;

import com.sw.output.domain.auth.dto.AuthResponseDTO;

public class AuthDTOConverter {
    public static AuthResponseDTO.TokenDTO toTokenDTO(String accessToken, String refreshToken) {
        return AuthResponseDTO.TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

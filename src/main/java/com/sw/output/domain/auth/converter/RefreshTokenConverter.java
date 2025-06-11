package com.sw.output.domain.auth.converter;

import java.time.LocalDateTime;

import com.sw.output.domain.auth.entity.RefreshToken;
import com.sw.output.domain.member.entity.Member;

public class RefreshTokenConverter {
    public static RefreshToken toRefreshToken(String refreshToken, LocalDateTime expiresAt, Member member) {
        return RefreshToken.builder()
                .refreshToken(refreshToken)
                .expiresAt(expiresAt)
                .member(member)
                .build();
    }
}

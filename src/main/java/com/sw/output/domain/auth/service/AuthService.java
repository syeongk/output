package com.sw.output.domain.auth.service;

import com.sw.output.domain.auth.converter.AuthDTOConverter;
import com.sw.output.domain.auth.dto.AuthResponseDTO;
import com.sw.output.domain.auth.entity.RefreshToken;
import com.sw.output.domain.auth.repository.RefreshTokenRepository;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.AuthErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void logout() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        refreshTokenRepository.deleteByMemberEmail(email);
    }

    @Transactional
    public AuthResponseDTO.TokenDTO reissue(String refreshToken) {
        // 토큰 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException(AuthErrorCode.INVALID_TOKEN);
        }

        // refresh token 조회
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.INVALID_TOKEN));

        // 토큰 만료 시간 검증
        if (refreshTokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException(AuthErrorCode.EXPIRED_TOKEN);
        }

        // 회원 조회
        Member member = refreshTokenEntity.getMember();

        // 새로운 토큰 발급
        String newAccessToken = jwtTokenProvider.createAccessToken(member.getEmail());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

        // refresh token 저장
        LocalDateTime expire = jwtTokenProvider.parseClaims(newRefreshToken)
                .getExpiration()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        refreshTokenEntity.updateToken(newRefreshToken, expire);

        // 토큰 발급
        return AuthDTOConverter.toTokenDTO(newAccessToken, newRefreshToken);
    }
}

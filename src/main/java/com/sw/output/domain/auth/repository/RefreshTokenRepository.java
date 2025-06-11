package com.sw.output.domain.auth.repository;

import com.sw.output.domain.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByMemberEmail(String email);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    Optional<RefreshToken> findByMemberId(Long memberId);
}

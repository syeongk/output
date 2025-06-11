package com.sw.output.domain.auth.entity;

import com.sw.output.domain.common.BaseEntity;
import com.sw.output.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken extends BaseEntity {
    @Column(nullable = false)
    private String refreshToken; // 리프레시 토큰 값

    @Column(nullable = false)
    private LocalDateTime expiresAt; // 만료 시간

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 회원

    public void updateToken(String refreshToken, LocalDateTime expiresAt) {
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }
}

package com.sw.output.domain.member.entity;

import static com.sw.output.domain.member.entity.Role.USER;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sw.output.domain.common.SoftDeleteEntity;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.report.entity.Report;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends SoftDeleteEntity {
    @Column(nullable = false, length = 100, unique = true)
    private String email; // 소셜 계정 이메일

    @Column(nullable = false, length = 30)
    private String nickname; // 닉네임

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = USER; // 권한

    @Column(nullable = false)
    @Builder.Default
    private Integer warningCount = 0; // 경고 횟수

    @Column(nullable = false)
    @Builder.Default
    private Boolean isSuspended = false; // 정지 여부

    @Column
    private LocalDateTime suspendedUntil; // 정지 해제일

    @Column
    private String picture;

    // 면접 세트와 1:N 연관관계
    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<InterviewSet> interviewSets = new ArrayList<>();

    // 결과 리포트와 1:N 연관관계
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void warn() {
        this.warningCount++;
    }
}

package com.sw.output.domain.member.entity;

import com.sw.output.domain.common.SoftDeleteEntity;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.report.entity.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sw.output.domain.member.entity.Role.USER;

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
        if (this.warningCount == 3) {
            this.suspendedUntil = LocalDateTime.now().plusDays(3);
        } else if (this.warningCount == 5) {
            this.suspendedUntil = LocalDateTime.now().plusDays(7);
        } else if (this.warningCount == 7) {
            this.suspendedUntil = LocalDateTime.now().plusDays(30);
        } else if (this.warningCount >= 10) {
            this.suspendedUntil = LocalDateTime.now().plusYears(1000);
        }
    }
}

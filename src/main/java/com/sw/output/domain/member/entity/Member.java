package com.sw.output.domain.member.entity;

import com.sw.output.domain.BaseEntity;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.report.entity.Report;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자 ID

    @Column(nullable = false, length = 100, unique = true)
    private String email; // 소셜 계정 이메일

    @Column(nullable = false, length = 100)
    private String nickname; // 닉네임

    @Column(nullable = false)
    private Boolean isDeleted = false; // 계정 탈퇴 여부

    @Column
    private LocalDateTime deletedAt; // 탈퇴일자

    // 면접 세트와 1:N 연관관계
    @OneToMany(mappedBy = "member")
    private List<InterviewSet> interviewSets = new ArrayList<>();

    // 결과 리포트와 1:N 연관관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();
}

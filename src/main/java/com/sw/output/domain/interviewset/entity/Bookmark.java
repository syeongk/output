package com.sw.output.domain.interviewset.entity;

import com.sw.output.domain.BaseEntity;
import com.sw.output.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Bookmark extends BaseEntity {
    // 사용자와 N:1 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 면접세트와 N:1 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_set_id", nullable = false)
    private InterviewSet interviewSet;
}

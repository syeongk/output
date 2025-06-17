package com.sw.output.domain.complaint.entity;

import static com.sw.output.domain.complaint.entity.ComplaintStatus.PENDING;

import com.sw.output.domain.common.SoftDeleteEntity;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Complaint extends SoftDeleteEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplaintType type; // 신고 유형

    @Column(nullable = false)
    private String content; // 신고 내용

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ComplaintStatus status = PENDING; // 처리 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 신고자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_set_id", nullable = false)
    private InterviewSet interviewSet; // 신고 면접 세트

    public void updateStatus(ComplaintStatus status) {
        this.status = status;
    }
}

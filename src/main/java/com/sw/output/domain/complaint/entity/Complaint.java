package com.sw.output.domain.complaint.entity;

import com.sw.output.domain.BaseEntity;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.sw.output.domain.complaint.entity.ComplaintStatus.PENDING;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Complaint extends BaseEntity {
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
}

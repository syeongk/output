package com.sw.output.domain.report.entity;

import com.sw.output.domain.BaseEntity;
import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import jakarta.persistence.*;
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
public class Feedback extends BaseEntity {
    @Column(nullable = false, columnDefinition = "TEXT")
    private String memberAnswer; // 사용자 답변

    @Column(nullable = false, columnDefinition = "TEXT")
    private String feedbackContent; // 피드백 내용

    @Column(nullable = false, columnDefinition = "TEXT")
    private String promptContent; // 프롬프트 내용

    // 결과 리포트와 N:1 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    // 질문 답변과 N:1 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_answer_id", nullable = false)
    private QuestionAnswer questionAnswer; // 질문 답변
}


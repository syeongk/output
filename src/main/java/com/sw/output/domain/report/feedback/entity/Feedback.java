package com.sw.output.domain.feedback.entity;

import com.sw.output.domain.BaseEntity;
import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import com.sw.output.domain.report.entity.Report;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 피드백 ID

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer_content; // 답변 내용

    @Column(nullable = false, columnDefinition = "TEXT")
    private String feedback_content; // 피드백 내용

    @Column(nullable = false, columnDefinition = "TEXT")
    private String prompt_content; // 피드백 내용

    // 결과 리포트와 N:1 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    // 질문 답변과 N:1 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_answer_id", nullable = false)
    private QuestionAnswer questionAnswer; // 질문 답변
}


package com.sw.output.domain.interviewset.entity;

import com.sw.output.domain.BaseEntity;
import com.sw.output.domain.feedback.entity.Feedback;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class QuestionAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 질문 답변 ID

    // 면접세트와 N:1 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_set_id", nullable = false)
    private InterviewSet interviewSet; // 면접 세트

    @Column(nullable = false, length = 100)
    private String questionTitle; // 질문 제목

    @Column(columnDefinition = "TEXT")
    private String answerContent; // 답변 내용 (NULL 허용)

    @Column(nullable = false)
    @Builder.Default
    private Boolean isAiCreated = false; // AI 생성 여부

    // 피드백과 1:N 연관관계
    @OneToMany(mappedBy = "questionAnswer", orphanRemoval = true)
    @Builder.Default
    private List<Feedback> feedbacks = new ArrayList<>();
}

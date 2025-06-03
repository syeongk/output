package com.sw.output.domain.interviewset.entity;

import com.sw.output.domain.BaseEntity;
import com.sw.output.domain.interviewset.dto.QuestionAnswerDTO;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.report.entity.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sw.output.domain.interviewset.converter.QuestionAnswerConverter.toQuestionAnswer;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InterviewSet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 면접 세트 ID

    // 사용자와 N:1 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 자기참조(부모 면접세트)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private InterviewSet parent;

    @Column(nullable = false, length = 50)
    private String title; // 면접 세트 제목

    @Column(nullable = false)
    @Builder.Default
    private Boolean isAnswerPublic = false; // 답변 공개 여부

    @Column(nullable = false)
    @Builder.Default
    private Integer bookmarkCount = 0; // 북마크 수

    @Column
    private LocalDateTime deletedAt; // 탈퇴일자

    @Column(nullable = false)
    @Builder.Default
    private Integer mockCount = 0; // 모의 면접 횟수

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InterviewCategory interviewCategory; // 면접 카테고리

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobCategory jobCategory; // 직무 카테고리

    @Column(nullable = false)
    @Builder.Default
    private Boolean isHidden = false; // 숨김 여부

    // 질문 답변과 1:N 연관관계
    @OneToMany(mappedBy = "interviewSet", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    // 결과 리포트와 1:N 연관관계
    @OneToMany(mappedBy = "interviewSet", orphanRemoval = true)
    @Builder.Default
    private List<Report> reports = new ArrayList<>();

    // 북마크와 1:N 연관관계
    @OneToMany(mappedBy = "interviewSet", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Bookmark> bookmarks = new ArrayList<>();

    /**
     * 질문 답변을 설정합니다.
     *
     * @param questionAnswersDTO 설정할 질문 답변 목록
     */
    public void setQuestionAnswers(List<QuestionAnswerDTO> questionAnswersDTO) {
        List<QuestionAnswer> questionAnswers = questionAnswersDTO.stream()
                .map(questionAnswer -> toQuestionAnswer(this, questionAnswer))
                .toList();

        this.questionAnswers.addAll(questionAnswers);
    }

    /**
     * 답변 공개 여부를 설정합니다.
     *
     * @param isAnswerPublic 답변 공개 여부
     */
    public void setIsAnswerPublic(Boolean isAnswerPublic) {
        this.isAnswerPublic = isAnswerPublic;
    }

    /**
     * 면접 세트 제목을 설정합니다.
     *
     * @param title 설정할 면접 세트 제목
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public void addBookmarkCount() {
        this.bookmarkCount += 1;
    }

    public void subtractBookmarkCount() {
        this.bookmarkCount -= 1;
    }

    public void increaseMockCount() {
        this.mockCount += 1;
    }
}

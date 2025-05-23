package com.sw.output.domain.interviewset.entity;

import com.sw.output.domain.BaseEntity;
import com.sw.output.domain.interviewset.dto.QuestionAnswerDTO;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.report.entity.Report;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sw.output.domain.interviewset.converter.InterviewSetInterviewCategoryConverter.toInterviewSetInterviewCategory;
import static com.sw.output.domain.interviewset.converter.InterviewSetJobCategoryConverter.toInterviewSetJobCategory;
import static com.sw.output.domain.interviewset.converter.QuestionAnswerConverter.toQuestionAnswer;

@Entity
@Getter
@Builder
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
    private Boolean isAnswerPublic = false; // 답변 공개 여부

    @Column(nullable = false)
    private Integer bookmarkCount = 0; // 북마크 수

    @Column(nullable = false)
    private Boolean isDeleted = false; // 삭제 여부

    @Column
    private LocalDateTime deletedAt; // 탈퇴일자

    @Column(nullable = false)
    private Integer mockCount = 0; // 모의 면접 횟수

    // 면접 세트 면접 카테고리와 1:N 연관관계
    @OneToMany(mappedBy = "interviewSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewSetInterviewCategory> interviewSetInterviewCategories = new ArrayList<>();

    // 면접 세트 직무 카테고리와 1:N 연관관계
    @OneToMany(mappedBy = "interviewSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewSetJobCategory> interviewSetJobCategories = new ArrayList<>();

    // 질문 답변과 1:N 연관관계
    @OneToMany(mappedBy = "interviewSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    // 결과 리포트와 1:N 연관관계
    @OneToMany(mappedBy = "interviewSet", orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    // 북마크와 1:N 연관관계
    @OneToMany(mappedBy = "interviewSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    /**
     * 면접 카테고리를 설정합니다.
     * <br>
     * 최대 2개까지 선택 가능하며, 기존 카테고리는 모두 제거됩니다.
     *
     * @param interviewCategories 설정할 면접 카테고리 목록
     * @throws BusinessException 카테고리가 2개를 초과하는 경우
     */
    public void setInterviewSetInterviewCategories(List<InterviewCategory> interviewCategories) {
        List<InterviewSetInterviewCategory> interviewSetInterviewCategories = interviewCategories
                .stream()
                .map(category -> toInterviewSetInterviewCategory(this, category))
                .toList();

        if (interviewSetInterviewCategories.size() > 2) {
            throw new BusinessException(InterviewSetErrorCode.INVALID_INTERVIEW_CATEGORY_COUNT);
        }

        this.interviewSetInterviewCategories.clear();
        this.interviewSetInterviewCategories.addAll(interviewSetInterviewCategories);
    }

    /**
     * 직무 카테고리를 설정합니다.
     * <br>
     * 최대 2개까지 선택 가능하며, 기존 카테고리는 모두 제거됩니다.
     *
     * @param jobCategories 설정할 직무 카테고리 목록
     * @throws BusinessException 카테고리가 2개를 초과하는 경우
     */
    public void setInterviewSetJobCategories(List<JobCategory> jobCategories) {
        List<InterviewSetJobCategory> interviewSetJobCategories = jobCategories.stream()
                .map(category -> toInterviewSetJobCategory(this, category))
                .toList();

        if (interviewSetJobCategories.size() > 2) {
            throw new BusinessException(InterviewSetErrorCode.INVALID_JOB_CATEGORY_COUNT);
        }

        this.interviewSetJobCategories.clear();
        this.interviewSetJobCategories.addAll(interviewSetJobCategories);
    }

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

    public void softDelete() {
        this.isDeleted = true;
    }

    public void addBookmarkCount() {
        this.bookmarkCount += 1;
    }

    public void subtractBookmarkCount() {
        this.bookmarkCount -= 1;
    }
}

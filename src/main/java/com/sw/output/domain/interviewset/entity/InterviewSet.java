package com.sw.output.domain.interviewset.entity;

import com.sw.output.domain.BaseEntity;
import com.sw.output.domain.bookmark.entity.Bookmark;
import com.sw.output.domain.mapping.entity.InterviewSetInterviewCategory;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.questionanswer.entity.QuestionAnswer;
import com.sw.output.domain.report.entity.Report;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
}

package com.sw.output.domain.faq.entity;

import com.sw.output.admin.faq.dto.AdminFAQRequestDTO;
import com.sw.output.domain.common.SoftDeleteEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Faq extends SoftDeleteEntity {
    @Column(nullable = false)
    private String questionTitle; // 질문 제목

    @Column(nullable = false)
    private String answerContent; // 답변 내용

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FaqCategory faqCategory; // 카테고리

    public void update(AdminFAQRequestDTO.FaqDTO request) {
        this.questionTitle = request.getQuestionTitle();
        this.answerContent = request.getAnswerContent();
        this.faqCategory = request.getFaqCategory();
    }
}

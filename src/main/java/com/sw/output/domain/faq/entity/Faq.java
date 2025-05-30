package com.sw.output.domain.faq.entity;

import com.sw.output.domain.BaseEntity;
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
public class Faq extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // FAQ ID

    @Column(nullable = false)
    private String questionTitle; // 질문 제목

    @Column(nullable = false)
    private String answerContent; // 답변 내용

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FaqCategory faqCategory; // 카테고리
}

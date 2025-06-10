package com.sw.output.domain.faq.entity;

import lombok.Getter;

@Getter
public enum FaqCategory {
    ALL("전체"),
    MEMBER("회원"),
    INTERVIEW_SET("면접 세트"),
    MOCK_INTERVIEW("모의 면접"),
    SERVICE("서비스 이용");

    private final String category;

    FaqCategory(String category) {
        this.category = category;
    }
}

package com.sw.output.domain.interviewset.entity;

import lombok.Getter;

@Getter
public enum InterviewCategory {
    PRACTICAL("실무"),
    TECHNICAL("기술"),
    PROJECT("프로젝트"),
    PERSONALITY("인성"),
    EXECUTIVE("임원"),
    COMPANY("회사");

    private final String name;

    InterviewCategory(String name) {
        this.name = name;
    }
}

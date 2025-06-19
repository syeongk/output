package com.sw.output.domain.interviewset.entity;

public enum InterviewCategory {
    PRACTICAL("실무"),
    TECHNICAL("기술"),
    PROJECT("프로젝트"),
    PERSONALITY("인성"),
    EXECUTIVE("임원"),
    COMPANY("회사");

    private final String category;

    InterviewCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}

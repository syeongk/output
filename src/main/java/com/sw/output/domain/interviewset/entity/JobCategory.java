package com.sw.output.domain.interviewset.entity;

public enum JobCategory {
    BACKEND("백엔드"),
    FRONTEND("프론트엔드"),
    GAME("게임"),
    SECURITY("보안"),
    DEVOPS("DevOps"),
    DATA_ANALYSIS("데이터 분석"),
    MACHINE_LEARNING("머신러닝"),
    QA("QA"),
    DBA("DBA"),
    CLOUD("클라우드"),
    BLOCKCHAIN("블록체인"),
    AI("AI"),
    EMBEDDED("임베디드");

    private final String category;

    JobCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
package com.sw.output.domain.complain.entity;

import lombok.Getter;

@Getter
public enum ComplainStatus {
    PENDING("처리전"),
    COMPLETED("처리완료");

    private final String status;

    ComplainStatus(String status) {
        this.status = status;
    }
}

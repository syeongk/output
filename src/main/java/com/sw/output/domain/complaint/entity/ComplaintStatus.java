package com.sw.output.domain.complaint.entity;

import lombok.Getter;

@Getter
public enum ComplaintStatus {
    PENDING("처리전"),
    COMPLETED("처리완료");

    private final String status;

    ComplaintStatus(String status) {
        this.status = status;
    }
}

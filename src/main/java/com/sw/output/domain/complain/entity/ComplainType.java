package com.sw.output.domain.complain.entity;

import lombok.Getter;

@Getter
public enum ComplainType {
    INAPPROPRIATE_CONTENT("부적절한 내용"),
    INCORRECT_INFORMATION("정확하지 않은 정보"),
    PERSONAL_INFO_EXPOSURE("개인정보 노출"),
    SPAM_AND_AD("스팸 및 광고"),
    OTHER("기타");

    private final String type;

    ComplainType(String type) {
        this.type = type;
    }
}
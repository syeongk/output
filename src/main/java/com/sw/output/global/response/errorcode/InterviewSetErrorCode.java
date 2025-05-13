package com.sw.output.global.response.errorcode;

import com.sw.output.global.response.BaseCode;
import org.springframework.http.HttpStatus;

public enum InterviewSetErrorCode implements BaseCode {
    INTERVIEW_SET_NOT_FOUND(HttpStatus.NOT_FOUND, "INTERVIEW_SET_4001", "면접 세트를 찾을 수 없습니다."),
    INTERVIEW_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "INTERVIEW_SET_4002", "면접 카테고리를 찾을 수 없습니다."),
    JOB_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "INTERVIEW_SET_4003", "직무 카테고리를 찾을 수 없습니다."),
    INVALID_INTERVIEW_CATEGORY_COUNT(HttpStatus.BAD_REQUEST, "INTERVIEW_SET_4004", "면접 카테고리는 1-2개만 선택 가능합니다."),
    INVALID_JOB_CATEGORY_COUNT(HttpStatus.BAD_REQUEST, "INTERVIEW_SET_4005", "직무 카테고리는 1-2개만 선택 가능합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    InterviewSetErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

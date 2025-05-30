package com.sw.output.global.response.errorcode;

import org.springframework.http.HttpStatus;

import com.sw.output.global.response.BaseCode;

public enum InterviewSetErrorCode implements BaseCode {
    INTERVIEW_SET_NOT_FOUND(HttpStatus.NOT_FOUND, "INTERVIEW_SET_4001", "면접 세트를 찾을 수 없습니다."),
    INTERVIEW_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "INTERVIEW_SET_4002", "면접 카테고리를 찾을 수 없습니다."),
    JOB_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "INTERVIEW_SET_4003", "직무 카테고리를 찾을 수 없습니다."),
    INTERVIEW_SET_DELETED(HttpStatus.NOT_FOUND, "INTERVIEW_SET_4004", "삭제된 면접 세트입니다."),
    INVALID_JOB_CATEGORY_SIZE(HttpStatus.BAD_REQUEST, "INTERVIEW_SET_4005", "직무 카테고리는 최대 2개만 가능합니다."),
    INVALID_INTERVIEW_SET_CATEGORY_SIZE(HttpStatus.BAD_REQUEST, "INTERVIEW_SET_4006", "면접 카테고리는 최대 2개만 가능합니다.");

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

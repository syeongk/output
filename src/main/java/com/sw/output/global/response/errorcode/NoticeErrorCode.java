package com.sw.output.global.response.errorcode;

import org.springframework.http.HttpStatus;

import com.sw.output.global.response.BaseCode;

public enum NoticeErrorCode implements BaseCode {
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTICE_4001", "공지사항을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    NoticeErrorCode(HttpStatus httpStatus, String code, String message) {
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

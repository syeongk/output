package com.sw.output.global.response.errorcode;

import com.sw.output.global.response.BaseCode;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements BaseCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_4001", "요청한 회원을 찾을 수 없습니다."),
    NICKNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "MEMBER_4002", "이미 존재하는 닉네임입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String code, String message) {
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

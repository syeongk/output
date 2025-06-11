package com.sw.output.global.response.errorcode;

import org.springframework.http.HttpStatus;

import com.sw.output.global.response.BaseCode;

public enum AuthErrorCode implements BaseCode {
    INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_4001", "유효하지 않은 ID Token 입니다"),
    GOOGLE_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH_4002", "Google 인증에 실패했습니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_4003", "토큰이 만료되었습니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_4004", "유효하지 않은 토큰입니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    AuthErrorCode(HttpStatus httpStatus, String code, String message) {
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

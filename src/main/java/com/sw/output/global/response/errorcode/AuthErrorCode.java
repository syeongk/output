package com.sw.output.global.response.errorcode;

import org.springframework.http.HttpStatus;

import com.sw.output.global.response.BaseCode;

public enum AuthErrorCode implements BaseCode {
    INVALID_ID_TOKEN(HttpStatus.BAD_REQUEST, "AUTH_4001", "유효하지 않은 ID Token 입니다"),
    GOOGLE_VERIFICATION_FAILED(HttpStatus.BAD_REQUEST, "AUTH_4002", "Google 인증에 실패했습니다"),
    ;

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

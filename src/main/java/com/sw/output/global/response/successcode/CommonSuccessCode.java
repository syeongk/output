package com.sw.output.global.response.successcode;

import com.sw.output.global.response.BaseCode;
import org.springframework.http.HttpStatus;

public enum CommonSuccessCode implements BaseCode {
    OK(HttpStatus.OK, "COMMON200", "요청이 성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, "COMMON201", "리소스가 성공적으로 생성되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON204", "요청이 성공적으로 처리되었습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CommonSuccessCode(HttpStatus httpStatus, String code, String message) {
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

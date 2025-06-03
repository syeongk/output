package com.sw.output.global.response.errorcode;

import com.sw.output.global.response.BaseCode;
import org.springframework.http.HttpStatus;

public enum ReportErrorCode implements BaseCode {
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT_4001", "결과 레포트를 찾을 수 없습니다."),
    REPORT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "REPORT_4002", "이미 삭제된 결과 레포트입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ReportErrorCode(HttpStatus httpStatus, String code, String message) {
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

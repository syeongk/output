package com.sw.output.global.response;

import org.springframework.http.HttpStatus;

/**
 * SuccessCode, ErrorCode 에서 구현할 공통 인터페이스
 */
public interface BaseCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}

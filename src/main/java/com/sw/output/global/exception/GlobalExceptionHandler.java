package com.sw.output.global.exception;

import com.sw.output.global.response.ApiResponse;
import com.sw.output.global.response.errorcode.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 서버 전체에서 발생하는 예외를 처리하는 공통 핸들러
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 예외 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        log.warn("[BusinessException] code = {}, message = {}",
                e.getBaseCode().getCode(),
                e.getBaseCode().getMessage());

        return ResponseEntity
                .status(e.getBaseCode().getHttpStatus())
                .body(ApiResponse.fail(e.getBaseCode()));
    }

    // 시스템 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("[Exception] message = {}", e.getMessage(), e);

        return ResponseEntity
                .status(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }
}

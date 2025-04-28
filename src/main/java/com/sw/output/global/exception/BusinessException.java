package com.sw.output.global.exception;

import com.sw.output.global.response.BaseCode;
import lombok.Getter;

/**
 * 비즈니스 로직 상 발생할 수 있는 예외
 */
@Getter
public class BusinessException extends RuntimeException {
    private final BaseCode baseCode;

    public BusinessException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }
}

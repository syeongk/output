package com.sw.output.global.response;

import com.sw.output.global.response.successcode.CommonSuccessCode;

import lombok.Builder;
import lombok.Getter;

/**
 * API 공통 응답 포맷
 */
@Getter
@Builder
public class ApiResponse<T> {
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    private T data;

    private ApiResponse(Boolean isSuccess, String code, String message, T data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ApiResponse(Boolean isSuccess, String code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, CommonSuccessCode.OK.getCode(), CommonSuccessCode.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(BaseCode baseCode, T data) {
        return new ApiResponse<>(true, baseCode.getCode(), baseCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, CommonSuccessCode.OK.getCode(), CommonSuccessCode.OK.getMessage(), null);
    }

    public static <T> ApiResponse<T> fail(BaseCode baseCode) {
        return new ApiResponse<>(false, baseCode.getCode(), baseCode.getMessage());
    }
}

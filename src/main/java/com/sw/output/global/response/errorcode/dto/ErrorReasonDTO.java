package com.sw.output.global.response.errorcode.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReasonDTO {

    private final boolean isSuccess;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

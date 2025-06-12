package com.sw.output.global.response.errorcode;

import com.sw.output.global.response.BaseCode;
import org.springframework.http.HttpStatus;

public enum QuestionAnswerErrorCode implements BaseCode {
    QUESTION_ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "FAQ_4001", "요청한 질문 답변을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    QuestionAnswerErrorCode(HttpStatus httpStatus, String code, String message) {
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

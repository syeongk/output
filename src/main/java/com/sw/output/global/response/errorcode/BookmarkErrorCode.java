package com.sw.output.global.response.errorcode;

import org.springframework.http.HttpStatus;

import com.sw.output.global.response.BaseCode;

public enum BookmarkErrorCode implements BaseCode {
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOKMARK_4001", "북마크가 존재하지 않습니다."),
    ALREADY_BOOKMARKED(HttpStatus.CONFLICT, "BOOKMARK_4002", "이미 북마크한 세트입니다."),
    ;
    
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    BookmarkErrorCode(HttpStatus httpStatus, String code, String message) {
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

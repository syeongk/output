package com.sw.output.global.exception;

import com.sw.output.global.response.errorcode.AuthErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {
    private final AuthErrorCode errorCode;

    public JwtAuthenticationException(AuthErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}

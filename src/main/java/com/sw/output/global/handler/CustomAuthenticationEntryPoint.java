package com.sw.output.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.output.global.exception.JwtAuthenticationException;
import com.sw.output.global.response.errorcode.AuthErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        // 기본값
        AuthErrorCode errorCode = AuthErrorCode.INVALID_TOKEN;

        // 예외 타입 확인
        if (authException instanceof JwtAuthenticationException jwtEx) {
            errorCode = jwtEx.getErrorCode();
        }

        // 응답 세팅
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        // 응답 바디
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("isSuccess", false);
        body.put("code", errorCode.getCode());
        body.put("message", errorCode.getMessage());
        body.put("data", null);

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}

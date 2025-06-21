package com.sw.output.global.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sw.output.domain.admin.Admin;
import com.sw.output.domain.admin.AdminRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminAuthInterceptor implements HandlerInterceptor {
    private final AdminRepository adminRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Long adminId = (Long) session.getAttribute("admin");

        // 로그인 체크
        if (adminId == null) {
            response.sendRedirect("/admin/auth/login");
            return false; // 컨트롤러 실행 안함
        }

        // (DB에서 삭제된 경우) 관리자 존재 여부 체크
        Admin admin = adminRepository.findById(adminId).orElse(null);

        if (admin == null) {
            session.invalidate();
            response.sendRedirect("/admin/auth/login");
            return false;
        }

        return true; // 컨트롤러 실행
    }
}

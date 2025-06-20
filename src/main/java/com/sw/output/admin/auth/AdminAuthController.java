package com.sw.output.admin.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sw.output.domain.admin.Admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/auth")
public class AdminAuthController {
    private final AdminAuthService adminAuthService;

    // 관리자 로그인 폼
    @GetMapping("/login")
    public String adminLogin(Model model) {
        model.addAttribute("loginDTO", new AdminRequestDTO.LoginDTO());
        return "admin/auth/login-form";
    }

    // 관리자 로그인 처리
    @PostMapping("/login")
    public String adminLogin(@ModelAttribute AdminRequestDTO.LoginDTO request, HttpSession session) {
        Admin admin = adminAuthService.validateLogin(request);

        // 세션에 관리자 정보 저장
        session.setAttribute("admin", admin.getId());

        // 세션 만료 시간 설정 (30분)
        session.setMaxInactiveInterval(30 * 60);
        return "redirect:/admin/members";
    }

    @PostMapping("/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/auth/login-form";
    }
}

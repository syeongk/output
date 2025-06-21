package com.sw.output.admin.auth;

import com.sw.output.domain.admin.Admin;
import com.sw.output.global.exception.BusinessException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String adminLogin(@ModelAttribute AdminRequestDTO.LoginDTO request, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            Admin admin = adminAuthService.validateLogin(request);
            session.setAttribute("admin", admin.getId());
            session.setMaxInactiveInterval(30 * 60);
            return "redirect:/admin/members";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/auth/login";
        }
    }

    // 관리자 로그아웃 
    @GetMapping("/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/auth/login-form";
    }
}

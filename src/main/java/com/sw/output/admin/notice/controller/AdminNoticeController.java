package com.sw.output.admin.notice.controller;

import com.sw.output.admin.notice.dto.AdminNoticeRequestDTO;
import com.sw.output.admin.notice.service.AdminNoticeService;
import com.sw.output.domain.notice.entity.Notice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/notices")
@RequiredArgsConstructor
public class AdminNoticeController {
    private final AdminNoticeService adminNoticeService;

    // 공지사항 목록 페이지
    @GetMapping
    public String getNotices(Model model) {
        List<Notice> notices = adminNoticeService.getNotices();
        model.addAttribute("notices", notices);
        return "/admin/notice/notice-list";
    }

    // 공지사항 작성 폼
    @GetMapping("/new")
    public String showNoticeForm(Model model) {
        model.addAttribute("noticeDTO", new AdminNoticeRequestDTO.NoticeDTO());
        return "admin/notice/notice-form";
    }

    // 공지사항 등록 처리
    @PostMapping
    public String createNotice(
            @ModelAttribute @Valid AdminNoticeRequestDTO.NoticeDTO request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/notice/notice-form";
        }
        adminNoticeService.createNotice(request);
        return "redirect:/admin/notices";
    }
}

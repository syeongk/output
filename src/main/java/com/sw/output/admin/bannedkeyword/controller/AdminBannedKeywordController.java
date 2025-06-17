package com.sw.output.admin.bannedkeyword.controller;

import com.sw.output.admin.bannedkeyword.dto.AdminBannedKeywordRequestDTO;
import com.sw.output.admin.bannedkeyword.service.AdminBannedKeywordService;
import com.sw.output.domain.bannedkeyword.entity.BannedKeyword;
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
@RequestMapping("/admin/banned-keywords")
@RequiredArgsConstructor
public class AdminBannedKeywordController {
    private final AdminBannedKeywordService adminBannedKeywordService;

    // 금지 키워드 목록 조회
    @GetMapping
    public String getBannedKeywords(Model model) {
        List<BannedKeyword> bannedKeywords = adminBannedKeywordService.getBannedKeywords();
        model.addAttribute("bannedKeywords", bannedKeywords);
        return "admin/banned-keyword/banned-keyword-list";
    }

    // 금지 키워드 등록 폼
    @GetMapping("/new")
    public String showBannedKeywordForm(Model model) {
        model.addAttribute("bannedKeywordDTO", new AdminBannedKeywordRequestDTO.BannedKeywordDTO());
        return "admin/banned-keyword/banned-keyword-form";
    }

    // 금지 키워드 등록
    @PostMapping("/new")
    public String createBannedKeyword(@ModelAttribute @Valid AdminBannedKeywordRequestDTO.BannedKeywordDTO request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/banned-keyword/banned-keyword-form";
        }
        adminBannedKeywordService.createBannedKeyword(request);
        return "redirect:/admin/banned-keywords";
    }
}

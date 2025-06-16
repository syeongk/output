package com.sw.output.admin.faq.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sw.output.admin.faq.dto.AdminFAQRequestDTO;
import com.sw.output.admin.faq.service.AdminFAQService;
import com.sw.output.domain.faq.entity.Faq;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/faqs")
@RequiredArgsConstructor
public class AdminFAQController {
    private final AdminFAQService adminFAQService;

    // FAQ 목록 조회
    @GetMapping
    public String getFAQs(Model model) {
        List<Faq> faqs = adminFAQService.getFAQs();
        model.addAttribute("faqs", faqs);
        return "admin/faq/faq-list";
    }

    // FAQ 등록 폼
    @GetMapping("/new")
    public String showFaqForm(Model model) {
        model.addAttribute("faqDTO", new AdminFAQRequestDTO.FaqDTO());
        return "admin/faq/faq-form";
    }

    // FAQ 등록 처리
    @PostMapping
    public String createFaq(
            @ModelAttribute @Valid AdminFAQRequestDTO.FaqDTO request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/faq/faq-form";
        }
        adminFAQService.createFaq(request);
        return "redirect:/admin/faqs";
    }

    // FAQ 삭제
    @PostMapping("/{faqId}/delete")
    public String deleteFaq(@PathVariable Long faqId) {
        adminFAQService.deleteFaq(faqId);
        return "redirect:/admin/faqs";
    }

    // FAQ 복구
    @PostMapping("/{faqId}/restore")
    public String restoreFaq(@PathVariable Long faqId) {
        adminFAQService.restoreFaq(faqId);
        return "redirect:/admin/faqs";
    }

    // FAQ 수정 폼
    @GetMapping("/{faqId}/edit")
    public String showFaqEditForm(@PathVariable Long faqId, Model model) {
        Faq faq = adminFAQService.getFaqById(faqId);
        model.addAttribute("faqDTO", new AdminFAQRequestDTO.FaqDTO());
        model.addAttribute("faq", faq);
        return "admin/faq/faq-edit";
    }

    // FAQ 수정 처리
    @PostMapping("/{faqId}/edit")
    public String updateFaq(@PathVariable Long faqId, @ModelAttribute @Valid AdminFAQRequestDTO.FaqDTO request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/faq/faq-edit";
        }
        adminFAQService.updateFaq(faqId, request);
        return "redirect:/admin/faqs";
    }
}

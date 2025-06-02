package com.sw.output.admin.faq.controller;

import com.sw.output.admin.faq.dto.AdminFAQRequestDTO;
import com.sw.output.admin.faq.service.AdminFAQService;
import com.sw.output.domain.faq.entity.Faq;
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
@RequestMapping("/admin/faqs")
@RequiredArgsConstructor
public class AdminFAQController {
    private final AdminFAQService adminFAQService;

    @GetMapping
    public String getFAQs(Model model) {
        List<Faq> faqs = adminFAQService.getFAQs();
        model.addAttribute("faqs", faqs);
        return "admin/faq/faq-list";
    }

    @GetMapping("/new")
    public String showFaqForm(Model model) {
        model.addAttribute("faqDTO", new AdminFAQRequestDTO.FaqDTO());
        return "admin/faq/faq-form";
    }

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
}

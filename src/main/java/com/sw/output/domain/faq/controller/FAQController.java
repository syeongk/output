package com.sw.output.domain.faq.controller;

import com.sw.output.domain.faq.dto.FAQResponseDTO;
import com.sw.output.domain.faq.service.FAQService;
import com.sw.output.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/faqs")
@RequiredArgsConstructor
public class FAQController {
    private final FAQService faqService;

    @GetMapping
    public ApiResponse<List<FAQResponseDTO.FaqDTO>> getFAQs() {
        List<FAQResponseDTO.FaqDTO> response = faqService.getFAQs();
        return ApiResponse.success(response);
    }
}

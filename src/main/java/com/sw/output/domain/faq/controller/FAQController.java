package com.sw.output.domain.faq.controller;

import com.sw.output.domain.faq.dto.FAQResponseDTO;
import com.sw.output.domain.faq.entity.FaqCategory;
import com.sw.output.domain.faq.service.FAQService;
import com.sw.output.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/faqs")
@RequiredArgsConstructor
public class FAQController {
    private final FAQService faqService;

    @GetMapping
    public ApiResponse<FAQResponseDTO.FaqCursorDTO> getFAQs(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "ALL") FaqCategory faqCategory
    ) {
        FAQResponseDTO.FaqCursorDTO response = faqService.getFAQs(cursorId, cursorCreatedAt, pageSize, faqCategory);
        return ApiResponse.success(response);
    }
}

package com.sw.output.domain.faq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sw.output.domain.faq.dto.FAQResponseDTO;
import com.sw.output.domain.faq.entity.FaqCategory;
import com.sw.output.domain.faq.service.FAQService;
import com.sw.output.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/faqs")
@RequiredArgsConstructor
@Tag(name = "자주 묻는 질문")
public class FAQController {
    private final FAQService faqService;

    @GetMapping
    @Operation(summary = "FAQ 목록 조회 API", description = "")
    public ApiResponse<FAQResponseDTO.FaqCursorDTO> getFAQs(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "ALL") FaqCategory faqCategory
    ) {
        FAQResponseDTO.FaqCursorDTO response = faqService.getFAQs(cursorId, pageSize, faqCategory);
        return ApiResponse.success(response);
    }
}

package com.sw.output.domain.faq.dto;

import com.sw.output.domain.faq.entity.FaqCategory;

import lombok.Builder;
import lombok.Getter;

public class FAQResponseDTO {
    @Getter
    @Builder
    public static class FaqDTO {
        private String questionTitle;
        private String answerContent;
        private FaqCategory faqCategory;
    }
}

package com.sw.output.domain.faq.converter;

import com.sw.output.domain.faq.dto.FAQResponseDTO;
import com.sw.output.domain.faq.entity.Faq;

public class FAQDTOConverter {
    public static FAQResponseDTO.FaqDTO toFaqDTO(Faq faq) {
        return FAQResponseDTO.FaqDTO.builder()
                .questionTitle(faq.getQuestionTitle())
                .answerContent(faq.getAnswerContent())
                .faqCategory(faq.getFaqCategory())
                .build();
    }
}
package com.sw.output.domain.faq.converter;

import com.sw.output.domain.faq.dto.FAQResponseDTO;
import com.sw.output.domain.faq.entity.Faq;

import java.util.List;
import java.util.stream.Collectors;

public class FAQDTOConverter {
    public static FAQResponseDTO.FaqDTO toFaqDTO(Faq faq) {
        return FAQResponseDTO.FaqDTO.builder()
                .id(faq.getId())
                .questionTitle(faq.getQuestionTitle())
                .answerContent(faq.getAnswerContent())
                .faqCategory(faq.getFaqCategory())
                .build();
    }

    public static FAQResponseDTO.FaqCursorDTO toFaqCursorDTO(List<Faq> faqs, Long cursorId) {
        return FAQResponseDTO.FaqCursorDTO.builder()
                .faqs(faqs.stream()
                        .map(FAQDTOConverter::toFaqDTO)
                        .collect(Collectors.toList()))
                .nextCursor(cursorId)
                .build();
    }
}

package com.sw.output.domain.faq.converter;

import com.sw.output.domain.faq.dto.FAQResponseDTO;
import com.sw.output.domain.faq.entity.Faq;
import com.sw.output.global.dto.CommonResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

import static com.sw.output.global.converter.CommonConverter.toCreatedAtCursorDTO;

public class FAQDTOConverter {
    public static FAQResponseDTO.FaqDTO toFaqDTO(Faq faq) {
        return FAQResponseDTO.FaqDTO.builder()
                .id(faq.getId())
                .questionTitle(faq.getQuestionTitle())
                .answerContent(faq.getAnswerContent())
                .faqCategory(faq.getFaqCategory())
                .build();
    }

    public static FAQResponseDTO.FaqCursorDTO toFaqCursorDTO(List<Faq> faqs, Faq lastFaq) {
        CommonResponseDTO.CursorDTO nextCursor = null;
        if (lastFaq != null) {
            nextCursor = toCreatedAtCursorDTO(lastFaq.getId(), lastFaq.getCreatedAt());
        }

        return FAQResponseDTO.FaqCursorDTO.builder()
                .faqs(faqs.stream()
                        .map(FAQDTOConverter::toFaqDTO)
                        .collect(Collectors.toList()))
                .nextCursor(nextCursor)
                .build();
    }
}

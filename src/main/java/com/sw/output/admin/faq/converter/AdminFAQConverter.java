package com.sw.output.admin.faq.converter;

import com.sw.output.admin.faq.dto.AdminFAQRequestDTO;
import com.sw.output.domain.faq.entity.Faq;
import org.springframework.stereotype.Component;

@Component
public class AdminFAQConverter {
    public static Faq toFAQ(AdminFAQRequestDTO.FaqDTO request) {
        return Faq.builder()
                .questionTitle(request.getQuestionTitle())
                .answerContent(request.getAnswerContent())
                .faqCategory(request.getFaqCategory())
                .build();
    }
}

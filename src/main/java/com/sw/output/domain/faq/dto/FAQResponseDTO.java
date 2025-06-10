package com.sw.output.domain.faq.dto;

import com.sw.output.domain.faq.entity.FaqCategory;
import com.sw.output.global.dto.CommonResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class FAQResponseDTO {
    @Getter
    @Builder
    public static class FaqDTO {
        private Long id;
        private String questionTitle;
        private String answerContent;
        private FaqCategory faqCategory;
    }

    @Getter
    @Builder
    public static class FaqCursorDTO {
        private List<FaqDTO> faqs;
        private CommonResponseDTO.CursorDTO nextCursor;
    }
}

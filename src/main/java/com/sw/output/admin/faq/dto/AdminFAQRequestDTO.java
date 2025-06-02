package com.sw.output.admin.faq.dto;

import com.sw.output.domain.faq.entity.FaqCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class AdminFAQRequestDTO {
    @Getter
    @Setter
    public static class FaqDTO {
        @NotBlank(message = "질문 제목을 입력해주세요.")
        @Size(max = 50, message = "질문 제목은 50자 이하로 입력해주세요.")
        private String questionTitle;

        @NotBlank(message = "답변 내용을 입력해주세요.")
        @Size(max = 3000, message = "답변 내용은 3000자 이하로 입력해주세요.")
        private String answerContent;

        @NotNull(message = "카테고리를 선택해주세요.")
        private FaqCategory faqCategory;
    }
}

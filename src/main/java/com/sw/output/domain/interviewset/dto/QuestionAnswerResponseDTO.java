package com.sw.output.domain.interviewset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class QuestionAnswerResponseDTO {
    @Getter
    @Setter
    @Builder
    public static class QuestionAnswerDTO {
        private Long id;

        @NotBlank(message = "질문 제목은 필수 항목입니다")
        @Size(min = 1, max = 50, message = "질문 제목은 1~50자 사이여야 합니다")
        private String questionTitle;

        private String answerContent;
    }
}

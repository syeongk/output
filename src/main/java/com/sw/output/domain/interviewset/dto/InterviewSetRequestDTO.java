package com.sw.output.domain.interviewset.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

public class InterviewSetRequestDTO {
    @Getter
    public static class InterviewSetDTO {
        @Size(min = 1, max = 2, message = "면접 카테고리는 1~2개여야 합니다")
        private List<String> interviewCategories;

        @Size(min = 1, max = 2, message = "직무 카테고리는 1~2개여야 합니다")
        private List<String> jobCategories;

        @Size(min = 1, max = 50, message = "세트 제목은 1~50자 사이여야 합니다")
        private String title;

        @Valid
        @Size(min = 1, max = 100, message = "질문 답변은 1~100개여야 합니다")
        private List<QuestionAnswerDTO> questionAnswers;

        @NotNull(message = "답변 공개 여부는 필수 항목입니다")
        private Boolean isAnswerPublic;
    }
}

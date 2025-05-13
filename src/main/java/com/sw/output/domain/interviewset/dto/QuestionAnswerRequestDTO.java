package com.sw.output.domain.interviewset.dto;

import lombok.Getter;

public class QuestionAnswerRequestDTO {

    @Getter
    public static class QuestionAnswerDTO {
        private String questionTitle;
        private String answerContent;
    }
}

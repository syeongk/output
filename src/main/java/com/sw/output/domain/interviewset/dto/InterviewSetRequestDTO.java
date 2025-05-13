package com.sw.output.domain.interviewset.dto;

import lombok.Getter;

import java.util.List;

public class InterviewSetRequestDTO {
    @Getter
    public static class CreateInterviewSetDTO {
        private List<String> interviewCategories;
        private List<String> jobCategories;
        private String title;
        private List<QuestionAnswerDTO> questionAnswers;
        private Boolean isAnswerPublic;
    }
}

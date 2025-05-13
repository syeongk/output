package com.sw.output.domain.interviewset.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class InterviewSetResponseDTO {
    @Getter
    @Builder
    public static class CreateInterviewSetDTO {
        private Long interviewSetId;
    }

    @Getter
    @Builder
    public static class GetInterviewSetDTO {
        private Long interviewSetId;
        private String title;
        private List<String> interviewCategories;
        private List<String> jobCategories;
        private String nickname;
        private LocalDateTime createdAt;
        private Boolean isAnswerPublic;
        private List<QuestionAnswerDTO> questionAnswers;
    }
}

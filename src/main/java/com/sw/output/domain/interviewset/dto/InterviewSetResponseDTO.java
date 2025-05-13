package com.sw.output.domain.interviewset.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class InterviewSetResponseDTO {
    @Getter
    @Builder
    public static class CreateInterviewSetDTO {
        private Long id;
    }

    @Getter
    @Builder
    public static class GetInterviewSetDTO {
        private Long id;
        private String title;
        private List<String> interviewCategories;
        private List<String> jobCategories;
        private String nickname;
        private LocalDateTime createdAt;
        private Boolean isAnswerPublic;
        private List<QuestionAnswerDTO> questionAnswers;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GetInterviewSetSummaryDTO {
        private Long id;
        private String title;
        private String nickname;
        private Integer bookmarkCount;
        private LocalDateTime createdAt;
    }

}

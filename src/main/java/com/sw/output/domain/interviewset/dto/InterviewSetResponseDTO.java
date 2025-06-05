package com.sw.output.domain.interviewset.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sw.output.domain.interviewset.entity.InterviewCategory;
import com.sw.output.domain.interviewset.entity.JobCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class InterviewSetResponseDTO {
    @Getter
    @Builder
    public static class InterviewSetIdDTO {
        private Long id;
    }

    @Getter
    @Builder
    public static class GetInterviewSetDTO {
        private Long id;
        private String title;
        private InterviewCategory interviewCategory;
        private JobCategory jobCategory;
        private String nickname;
        @JsonFormat(pattern = "yyyy-MM-dd")
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
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class GetQuestionsDTO {
        private List<QuestionAnswerDTO> questionAnswers;
    }
}

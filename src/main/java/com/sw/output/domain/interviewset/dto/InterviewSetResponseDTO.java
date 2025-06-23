package com.sw.output.domain.interviewset.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
        private Long parentId;
        private String title;
        private InterviewCategory interviewCategory;
        private JobCategory jobCategory;
        private String nickname;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime createdAt;
        private Boolean isAnswerPublic;
        private Boolean isBookmarked;
        private List<QuestionAnswerResponseDTO.QuestionAnswerDTO> questionAnswers;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GetInterviewSetSummaryDTO {
        private Long interviewSetId;
        private String title;
        private String nickname;
        private Integer bookmarkCount;
        private Integer mockCount;
        private Boolean isAnswerPublic;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class GetQuestionsDTO {
        private List<String> questions;
    }

    @Getter
    @Builder
    public static class InterviewSetCursorDTO {
        private GetInterviewSetDTO interviewSet;
        private Long nextCursor;
    }

    @Getter
    @Builder
    public static class InterviewSetsCursorDTO {
        private List<GetInterviewSetSummaryDTO> interviewSets;
        private Long nextCursor;
    }
}

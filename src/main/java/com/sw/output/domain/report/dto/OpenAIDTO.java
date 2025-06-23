package com.sw.output.domain.report.dto;

import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.Report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OpenAIDTO {
    @Getter
    @Builder
    public static class AiFeedbackDTO {
        private Report report;
        private QuestionAnswer questionAnswer;
        private Feedback feedback;
    }

    @Getter
    @Builder
    public static class AiFeedbackResultDTO {
        private String prompt;
        private String feedback;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WhisperResponseDTO {
        private String text;
    }
}

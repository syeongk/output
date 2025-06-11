package com.sw.output.domain.report.dto;

import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import com.sw.output.domain.report.entity.Report;

import lombok.Builder;
import lombok.Getter;

public class ValidationResultDTO {
    @Getter
    @Builder
    public static class AiFeedbackValidationDTO {
        private Report report;
        private QuestionAnswer questionAnswer;
    }
}

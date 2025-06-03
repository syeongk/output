package com.sw.output.domain.report.dto;

import lombok.Getter;

public class ReportRequestDTO {
    @Getter
    public static class CreateAiFeedbackDTO {
        private String questionTitle;
        private String memberAnswer;
    }
}

package com.sw.output.domain.report.dto;

import lombok.Builder;
import lombok.Getter;

public class ReportResponseDTO {
    @Getter
    @Builder
    public static class GetReportDTO {
        private Long id; // 피드백 ID
        private String questionTitle;
        private String memberAnswer;
        private String feedbackContent;
    }
}

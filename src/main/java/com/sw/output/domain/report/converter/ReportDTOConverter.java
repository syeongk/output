package com.sw.output.domain.report.converter;

import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.domain.report.entity.Feedback;

public class ReportDTOConverter {
    public static ReportResponseDTO.GetReportDTO toGetReportDTO(Feedback feedback) {
        return ReportResponseDTO.GetReportDTO.builder()
                .id(feedback.getId())
                .questionTitle(feedback.getQuestionAnswer().getQuestionTitle())
                .memberAnswer(feedback.getMemberAnswer())
                .feedbackContent(feedback.getFeedbackContent())
                .build();
    }
}

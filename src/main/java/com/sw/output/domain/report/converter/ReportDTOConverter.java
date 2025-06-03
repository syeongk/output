package com.sw.output.domain.report.converter;

import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.Report;

public class ReportDTOConverter {
    public static ReportResponseDTO.GetReportDetailDTO toGetReportDetailDTO(Feedback feedback) {
        return ReportResponseDTO.GetReportDetailDTO.builder()
                .feedbackId(feedback.getId())
                .questionTitle(feedback.getQuestionAnswer().getQuestionTitle())
                .memberAnswer(feedback.getMemberAnswer())
                .feedbackContent(feedback.getFeedbackContent())
                .build();
    }

    public static ReportResponseDTO.GetReportDTO toGetReportDTO(Report report) {
        return ReportResponseDTO.GetReportDTO.builder()
                .interviewSetId(report.getInterviewSet().getId())
                .title(report.getInterviewSet().getTitle())
                .bookmarkCount(report.getInterviewSet().getBookmarkCount())
                .interviewSetCreatedAt(report.getInterviewSet().getCreatedAt())
                .reportId(report.getId())
                .reportCreatedAt(report.getCreatedAt())
                .build();
    }
}

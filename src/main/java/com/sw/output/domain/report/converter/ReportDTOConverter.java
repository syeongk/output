package com.sw.output.domain.report.converter;

import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.domain.report.entity.Report;

import java.util.List;
import java.util.stream.Collectors;

public class ReportDTOConverter {
    public static ReportResponseDTO.ReportDTO toReportDTO(Report report) {
        return ReportResponseDTO.ReportDTO.builder()
                .interviewSetId(report.getInterviewSet().getId())
                .title(report.getInterviewSet().getTitle())
                .bookmarkCount(report.getInterviewSet().getBookmarkCount())
                .interviewSetCreatedAt(report.getInterviewSet().getCreatedAt())
                .reportId(report.getId())
                .reportCreatedAt(report.getCreatedAt())
                .build();
    }

    public static ReportResponseDTO.ReportsDTO toReportsDTO(List<Report> reports, Long cursorId) {
        List<ReportResponseDTO.ReportDTO> reportDTOs = reports.stream()
                .map(ReportDTOConverter::toReportDTO)
                .collect(Collectors.toList());

        return ReportResponseDTO.ReportsDTO.builder()
                .reports(reportDTOs)
                .nextCursor(cursorId)
                .build();
    }

}

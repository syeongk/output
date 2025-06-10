package com.sw.output.domain.report.converter;

import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.domain.report.entity.Report;
import com.sw.output.global.dto.CommonResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

import static com.sw.output.global.converter.CommonConverter.toCreatedAtCursorDTO;

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

    public static ReportResponseDTO.ReportsDTO toReportsDTO(List<Report> reports, Report lastReport) {
        CommonResponseDTO.CursorDTO nextCursor = null;

        if (lastReport != null) {
            nextCursor = toCreatedAtCursorDTO(lastReport.getId(), lastReport.getCreatedAt());
        }

        List<ReportResponseDTO.ReportDTO> reportDTOs = reports.stream()
                .map(ReportDTOConverter::toReportDTO)
                .collect(Collectors.toList());

        return ReportResponseDTO.ReportsDTO.builder()
                .reports(reportDTOs)
                .nextCursor(nextCursor)
                .build();
    }

}

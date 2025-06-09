package com.sw.output.domain.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sw.output.global.dto.CommonResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ReportResponseDTO {
    @Getter
    @Builder
    public static class ReportDTO {
        private Long interviewSetId; // 면접 세트 ID
        private String title; // 면접 세트 제목
        private Integer bookmarkCount; // 면접세트 북마크 수
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime interviewSetCreatedAt; // 면접세트 생성일
        private Long reportId; // 레포트 ID
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime reportCreatedAt; // 레포트 생성일
    }

    @Getter
    @Builder
    public static class ReportsDTO {
        private List<ReportDTO> reports;
        private CommonResponseDTO.CursorDTO nextCursor;
    }
}

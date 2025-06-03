package com.sw.output.domain.report.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

public class ReportResponseDTO {
    @Getter
    @Builder
    public static class GetReportDetailDTO {
        private Long feedbackId; // 피드백 ID
        private String questionTitle; // 질문 제목
        private String memberAnswer; // 사용자 답변
        private String feedbackContent; // AI 피드백 내용
    }

    @Getter
    @Builder
    public static class GetReportDTO {
        private Long interviewSetId; // 면접 세트 ID
        private String title; // 면접 세트 제목
        private Integer bookmarkCount; // 면접세트 북마크 수
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime interviewSetCreatedAt; // 면접세트 생성일
        private Long reportId; // 레포트 ID
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime reportCreatedAt; // 레포트 생성일
    }
}

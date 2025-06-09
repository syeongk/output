package com.sw.output.domain.report.dto;

import java.util.List;

import com.sw.output.global.dto.CommonResponseDTO;

import lombok.Builder;
import lombok.Getter;

public class FeedbackResponseDTO {
    @Getter
    @Builder
    public static class FeedbackDTO {
        private Long feedbackId; // 피드백 ID
        private String questionTitle; // 질문 제목
        private String memberAnswer; // 사용자 답변
        private String feedbackContent; // AI 피드백 내용
    }

    @Getter
    @Builder
    public static class FeedbacksDTO {
        private List<FeedbackDTO> feedbacks;
        private CommonResponseDTO.CursorDTO nextCursor;
    }
}

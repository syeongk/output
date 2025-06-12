package com.sw.output.domain.report.dto;

import com.sw.output.domain.report.entity.FeedbackStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class FeedbackResponseDTO {
    @Getter
    @Builder
    public static class FeedbackDTO {
        private Long feedbackId; // 피드백 ID
        private Long questionAnswerId; // 질문 답변 ID
        private String questionTitle; // 질문 제목
        private String memberAnswer; // 사용자 답변
        private String feedbackContent; // AI 피드백 내용
        private FeedbackStatus feedbackStatus; // 피드백 상태
    }

    @Getter
    @Builder
    public static class FeedbacksDTO {
        private List<FeedbackDTO> feedbacks;
        private Long nextCursor;
    }
}

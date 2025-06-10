package com.sw.output.domain.report.converter;

import com.sw.output.domain.report.dto.FeedbackResponseDTO;
import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.global.dto.CommonResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

import static com.sw.output.global.converter.CommonConverter.toCreatedAtCursorDTO;

public class FeedbackDTOConverter {
    public static FeedbackResponseDTO.FeedbackDTO toFeedbackDTO(Feedback feedback) {
        return FeedbackResponseDTO.FeedbackDTO.builder()
                .feedbackId(feedback.getId())
                .questionTitle(feedback.getQuestionAnswer().getQuestionTitle())
                .memberAnswer(feedback.getMemberAnswer())
                .feedbackContent(feedback.getFeedbackContent())
                .build();
    }

    public static FeedbackResponseDTO.FeedbacksDTO toFeedbacksDTO(List<Feedback> feedbacks, Feedback lastFeedback) {
        CommonResponseDTO.CursorDTO nextCursor = null;

        if (lastFeedback != null) {
            nextCursor = toCreatedAtCursorDTO(lastFeedback.getId(), lastFeedback.getCreatedAt());
        }

        List<FeedbackResponseDTO.FeedbackDTO> feedbackDTOs = feedbacks.stream()
                .map(FeedbackDTOConverter::toFeedbackDTO)
                .collect(Collectors.toList());

        return FeedbackResponseDTO.FeedbacksDTO.builder()
                .feedbacks(feedbackDTOs)
                .nextCursor(nextCursor)
                .build();
    }
}

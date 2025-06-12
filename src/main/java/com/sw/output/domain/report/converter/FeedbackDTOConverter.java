package com.sw.output.domain.report.converter;

import com.sw.output.domain.report.dto.FeedbackResponseDTO;
import com.sw.output.domain.report.entity.Feedback;

import java.util.List;
import java.util.stream.Collectors;

public class FeedbackDTOConverter {
    public static FeedbackResponseDTO.FeedbackDTO toFeedbackDTO(Feedback feedback) {
        return FeedbackResponseDTO.FeedbackDTO.builder()
                .feedbackId(feedback.getId())
                .questionAnswerId(feedback.getQuestionAnswer().getId())
                .questionTitle(feedback.getQuestionAnswer().getQuestionTitle())
                .memberAnswer(feedback.getMemberAnswer())
                .feedbackContent(feedback.getFeedbackContent())
                .feedbackStatus(feedback.getFeedbackStatus())
                .build();
    }

    public static FeedbackResponseDTO.FeedbacksDTO toFeedbacksDTO(List<Feedback> feedbacks, Long nextCursor) {
        List<FeedbackResponseDTO.FeedbackDTO> feedbackDTOs = feedbacks.stream()
                .map(FeedbackDTOConverter::toFeedbackDTO)
                .collect(Collectors.toList());

        return FeedbackResponseDTO.FeedbacksDTO.builder()
                .feedbacks(feedbackDTOs)
                .nextCursor(nextCursor)
                .build();
    }
}

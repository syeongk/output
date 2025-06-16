package com.sw.output.domain.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.FeedbackStatus;
import com.sw.output.domain.report.repository.FeedbackRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.FeedbackErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public void updateFeedbackResult(Long feedbackId, String memberAnswer, String feedbackContent, String prompt) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new BusinessException(FeedbackErrorCode.FEEDBACK_NOT_FOUND));

        feedback.updateFeedback(memberAnswer, feedbackContent, prompt, FeedbackStatus.COMPLETED);
    }
}

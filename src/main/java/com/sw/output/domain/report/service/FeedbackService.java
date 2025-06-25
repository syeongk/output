package com.sw.output.domain.report.service;

import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.FeedbackStatus;
import com.sw.output.domain.report.repository.FeedbackRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.FeedbackErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public void updateFeedbackResult(Long feedbackId, String memberAnswer, String feedbackContent, String prompt) {
        log.info("[{}] end", Thread.currentThread().getName());
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new BusinessException(FeedbackErrorCode.FEEDBACK_NOT_FOUND));

        feedback.updateFeedback(memberAnswer, feedbackContent, prompt, FeedbackStatus.COMPLETED);
    }
}

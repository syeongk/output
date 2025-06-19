package com.sw.output.admin.report.service;

import org.springframework.stereotype.Service;

import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.repository.FeedbackRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.FeedbackErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminFeedbackService {
    private final FeedbackRepository feedbackRepository;

    public Feedback getFeedback(Long reportId) {
        return feedbackRepository.findById(reportId)
                .orElseThrow(() -> new BusinessException(FeedbackErrorCode.FEEDBACK_NOT_FOUND));
    }
}

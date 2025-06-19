package com.sw.output.admin.report.service;

import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminFeedbackService {
    private final FeedbackRepository feedbackRepository;

    public List<Feedback> getFeedback(Long reportId) {
        return feedbackRepository.findAllByReportId(reportId);
    }
}

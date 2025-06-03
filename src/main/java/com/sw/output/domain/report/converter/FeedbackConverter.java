package com.sw.output.domain.report.converter;

import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.Report;

public class FeedbackConverter {
    public static Feedback toFeedback(Report report, QuestionAnswer questionAnswer, String memberAnswer, String feedbackContent, String promptContent) {
        return Feedback.builder()
                .report(report)
                .questionAnswer(questionAnswer)
                .memberAnswer(memberAnswer)
                .feedbackContent(feedbackContent)
                .promptContent(promptContent)
                .build();
    }
}

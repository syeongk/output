package com.sw.output.domain.report.converter;

import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import com.sw.output.domain.report.dto.OpenAIDTO.AiFeedbackDTO;
import com.sw.output.domain.report.dto.OpenAIDTO.AiFeedbackResultDTO;
import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.Report;

public class OpenAIDTOConverter {
    public static AiFeedbackDTO toAiFeedbackDTO(Report report, QuestionAnswer questionAnswer, Feedback feedback) {
        return AiFeedbackDTO.builder()
                .report(report)
                .questionAnswer(questionAnswer)
                .feedback(feedback)
                .build();
    }

    public static AiFeedbackResultDTO toAiFeedbackResultDTO(String prompt, String feedback) {
        return AiFeedbackResultDTO.builder()
                .prompt(prompt)
                .feedback(feedback)
                .build();
    }
}

package com.sw.output.domain.report.converter;

import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import com.sw.output.domain.report.dto.ValidationResultDTO.AiFeedbackValidationDTO;
import com.sw.output.domain.report.entity.Report;

public class ValidationResultDTOConverter {
    public static AiFeedbackValidationDTO toAiFeedbackValidationDTO(Report report, QuestionAnswer questionAnswer) {
        return AiFeedbackValidationDTO.builder()
                .report(report)
                .questionAnswer(questionAnswer)
                .build();
    }
}

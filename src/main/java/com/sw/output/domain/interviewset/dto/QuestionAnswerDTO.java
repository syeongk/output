package com.sw.output.domain.interviewset.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionAnswerDTO {
    private String questionTitle;
    private String answerContent;
}

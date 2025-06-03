package com.sw.output.domain.interviewset.converter;

import com.sw.output.domain.interviewset.dto.QuestionAnswerDTO;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.entity.QuestionAnswer;

import java.util.ArrayList;

public class QuestionAnswerConverter {
    public static QuestionAnswer toQuestionAnswer(InterviewSet interviewSet, QuestionAnswerDTO questionAnswerDTO) {
        return QuestionAnswer.builder()
                .interviewSet(interviewSet)
                .questionTitle(questionAnswerDTO.getQuestionTitle())
                .answerContent(questionAnswerDTO.getAnswerContent())
                .feedbacks(new ArrayList<>())
                .build();
    }

    public static QuestionAnswerDTO toQuestionAnswerDTO(QuestionAnswer questionAnswer) {
        return QuestionAnswerDTO.builder()
                .questionTitle(questionAnswer.getQuestionTitle())
                .answerContent(questionAnswer.getAnswerContent())
                .build();
    }

    public static QuestionAnswerDTO toQuestionAnswerDTO(String questionTitle) {
        return QuestionAnswerDTO.builder()
                .questionTitle(questionTitle)
                .build();
    }
}

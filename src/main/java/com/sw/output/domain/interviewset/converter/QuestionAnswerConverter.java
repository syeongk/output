package com.sw.output.domain.interviewset.converter;

import com.sw.output.domain.interviewset.dto.QuestionAnswerRequestDTO;
import com.sw.output.domain.interviewset.dto.QuestionAnswerResponseDTO;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.entity.QuestionAnswer;

import java.util.ArrayList;

public class QuestionAnswerConverter {
    public static QuestionAnswer toQuestionAnswer(InterviewSet interviewSet, QuestionAnswerRequestDTO.QuestionAnswerCreateDTO questionAnswerDTO) {
        return QuestionAnswer.builder()
                .interviewSet(interviewSet)
                .questionTitle(questionAnswerDTO.getQuestionTitle())
                .answerContent(questionAnswerDTO.getAnswerContent())
                .feedbacks(new ArrayList<>())
                .build();
    }

    public static QuestionAnswerResponseDTO.QuestionAnswerDTO toQuestionAnswerDTO(QuestionAnswer questionAnswer) {
        return QuestionAnswerResponseDTO.QuestionAnswerDTO.builder()
                .id(questionAnswer.getId())
                .questionTitle(questionAnswer.getQuestionTitle())
                .answerContent(questionAnswer.getAnswerContent())
                .build();
    }

    public static QuestionAnswerResponseDTO.QuestionAnswerDTO toQuestionAnswerDTO(String questionTitle) {
        return QuestionAnswerResponseDTO.QuestionAnswerDTO.builder()
                .questionTitle(questionTitle)
                .build();
    }
}

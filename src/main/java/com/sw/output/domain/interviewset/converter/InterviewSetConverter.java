package com.sw.output.domain.interviewset.converter;

import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.dto.QuestionAnswerRequestDTO;
import com.sw.output.domain.interviewset.entity.*;
import com.sw.output.domain.mapping.entity.InterviewSetInterviewCategory;
import com.sw.output.domain.member.entity.Member;

import java.util.ArrayList;

public class InterviewSetConverter {

    public static InterviewSet toInterviewSet(Member member, InterviewSetRequestDTO.CreateInterviewSetDTO createInterviewSetDTO) {
        return InterviewSet.builder()
                .member(member)
                .parent(null)
                .title(createInterviewSetDTO.getTitle())
                .isAnswerPublic(createInterviewSetDTO.getIsAnswerPublic())
                .bookmarkCount(0)
                .interviewSetInterviewCategories(new ArrayList<>())
                .interviewSetJobCategories(new ArrayList<>())
                .questionAnswers(new ArrayList<>())
                .reports(new ArrayList<>())
                .bookmarks(new ArrayList<>())
                .build();
    }

    public static InterviewSetResponseDTO.CreateInterviewSetDTO toCreateResponse(Long interviewSetId) {
        return InterviewSetResponseDTO.CreateInterviewSetDTO.builder()
                .interviewSetId(interviewSetId)
                .build();
    }

    public static InterviewSetInterviewCategory toInterviewSetInterviewCategory(InterviewSet interviewSet, InterviewCategory interviewCategory) {
        return InterviewSetInterviewCategory.builder()
                .interviewSet(interviewSet)
                .interviewCategory(interviewCategory)
                .build();
    }

    public static InterviewSetJobCategory toInterviewSetJobCategory(InterviewSet interviewSet, JobCategory jobCategory) {
        return InterviewSetJobCategory.builder()
                .interviewSet(interviewSet)
                .jobCategory(jobCategory)
                .build();
    }

    public static QuestionAnswer toQuestionAnswer(InterviewSet interviewSet, QuestionAnswerRequestDTO.QuestionAnswerDTO questionAnswerDTO) {
        return QuestionAnswer.builder()
                .interviewSet(interviewSet)
                .questionTitle(questionAnswerDTO.getQuestionTitle())
                .answerContent(questionAnswerDTO.getAnswerContent())
                .feedbacks(new ArrayList<>())
                .build();
    }
}

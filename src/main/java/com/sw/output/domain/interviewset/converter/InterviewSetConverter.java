package com.sw.output.domain.interviewset.converter;

import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.member.entity.Member;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.sw.output.domain.interviewset.converter.QuestionAnswerConverter.toQuestionAnswerDTO;

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

    public static InterviewSetResponseDTO.CreateInterviewSetDTO toCreateInterviewSetResponse(Long interviewSetId) {
        return InterviewSetResponseDTO.CreateInterviewSetDTO.builder()
                .interviewSetId(interviewSetId)
                .build();
    }

    public static InterviewSetResponseDTO.GetInterviewSetDTO toGetInterviewSetResponse(InterviewSet interviewSet) {
        return InterviewSetResponseDTO.GetInterviewSetDTO.builder()
                .interviewSetId(interviewSet.getId())
                .title(interviewSet.getTitle())
                .interviewCategories(
                        interviewSet.getInterviewSetInterviewCategories().stream()
                                .map(category -> category.getInterviewCategory())
                                .map(category -> category.getName())
                                .collect(Collectors.toList())
                )
                .jobCategories(
                        interviewSet.getInterviewSetJobCategories().stream()
                                .map(category -> category.getJobCategory())
                                .map(category -> category.getName())
                                .collect(Collectors.toList())
                )
                .nickname(interviewSet.getMember().getNickname())
                .createdAt(interviewSet.getCreatedAt())
                .isAnswerPublic(interviewSet.getIsAnswerPublic())
                .questionAnswers(
                        interviewSet.getQuestionAnswers().stream()
                                .map(questionAnswer -> toQuestionAnswerDTO(questionAnswer))
                                .collect(Collectors.toList()))
                .build();
    }
}

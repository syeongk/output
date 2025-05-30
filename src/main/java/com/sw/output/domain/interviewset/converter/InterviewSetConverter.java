package com.sw.output.domain.interviewset.converter;

import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.member.entity.Member;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class InterviewSetConverter {

    public static InterviewSet toInterviewSet(
            Member member,
            InterviewSetRequestDTO.InterviewSetDTO interviewSetDTO) {

        return InterviewSet.builder()
                .member(member)
                .parent(null)
                .title(interviewSetDTO.getTitle())
                .isAnswerPublic(interviewSetDTO.getIsAnswerPublic())
                .bookmarkCount(0)
                .isDeleted(false)
                .mockCount(0)
                .interviewCategory(interviewSetDTO.getInterviewCategory())
                .jobCategory(interviewSetDTO.getJobCategory())
                .questionAnswers(new ArrayList<>())
                .reports(new ArrayList<>())
                .bookmarks(new ArrayList<>())
                .build();
    }

    public static InterviewSet toInterviewSet(
            Member member,
            InterviewSetRequestDTO.InterviewSetDTO interviewSetDTO,
            InterviewSet parentInterviewSet) {

        return InterviewSet.builder()
                .member(member)
                .parent(parentInterviewSet)
                .title(interviewSetDTO.getTitle())
                .isAnswerPublic(interviewSetDTO.getIsAnswerPublic())
                .bookmarkCount(0)
                .isDeleted(false)
                .mockCount(0)
                .interviewCategory(interviewSetDTO.getInterviewCategory())
                .jobCategory(interviewSetDTO.getJobCategory())
                .questionAnswers(new ArrayList<>())
                .reports(new ArrayList<>())
                .bookmarks(new ArrayList<>())
                .build();
    }

    public static InterviewSetResponseDTO.InterviewSetIdDTO toInterviewSetIdResponse(Long interviewSetId) {
        return InterviewSetResponseDTO.InterviewSetIdDTO.builder()
                .id(interviewSetId)
                .build();
    }

    public static InterviewSetResponseDTO.GetInterviewSetDTO toGetInterviewSetResponse(InterviewSet interviewSet) {
        return InterviewSetResponseDTO.GetInterviewSetDTO.builder()
                .id(interviewSet.getId())
                .title(interviewSet.getTitle())
                .interviewCategory(interviewSet.getInterviewCategory())
                .jobCategory(interviewSet.getJobCategory())
                .nickname(interviewSet.getMember().getNickname())
                .createdAt(interviewSet.getCreatedAt())
                .isAnswerPublic(interviewSet.getIsAnswerPublic())
                .questionAnswers(
                        interviewSet.getQuestionAnswers().stream()
                                .map(QuestionAnswerConverter::toQuestionAnswerDTO)
                                .collect(Collectors.toList()))
                .build();
    }
}

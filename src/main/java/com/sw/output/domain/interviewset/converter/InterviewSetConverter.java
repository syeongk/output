package com.sw.output.domain.interviewset.converter;

import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import com.sw.output.domain.interviewset.entity.QuestionAnswerSortType;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.global.dto.CommonResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sw.output.global.converter.CommonConverter.toCreatedAtCursorDTO;
import static com.sw.output.global.converter.CommonConverter.toTitleCursorDTO;

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

    public static InterviewSetResponseDTO.GetInterviewSetDTO toGetInterviewSetResponse(InterviewSet interviewSet, List<QuestionAnswer> questionAnswers) {
        return InterviewSetResponseDTO.GetInterviewSetDTO.builder()
                .id(interviewSet.getId())
                .parentId(interviewSet.getParent() != null ? interviewSet.getParent().getId() : null)
                .title(interviewSet.getTitle())
                .interviewCategory(interviewSet.getInterviewCategory())
                .jobCategory(interviewSet.getJobCategory())
                .nickname(interviewSet.getMember().getNickname())
                .createdAt(interviewSet.getCreatedAt())
                .isAnswerPublic(interviewSet.getIsAnswerPublic())
                .questionAnswers(
                        questionAnswers.stream()
                                .map(QuestionAnswerConverter::toQuestionAnswerDTO)
                                .collect(Collectors.toList()))
                .build();
    }

    public static InterviewSetResponseDTO.GetQuestionsDTO toGetQuestionsDTO(List<String> questions) {
        return InterviewSetResponseDTO.GetQuestionsDTO.builder()
                .questions(questions)
                .build();
    }

    public static InterviewSetResponseDTO.InterviewSetCursorDTO toInterviewSetCursorResponse(InterviewSet interviewSet, List<QuestionAnswer> questionAnswers, QuestionAnswer lastQuestionAnswer, QuestionAnswerSortType questionAnswerSortType) {
        CommonResponseDTO.CursorDTO nextCursor = null;

        if (lastQuestionAnswer != null) {
            if (questionAnswerSortType == QuestionAnswerSortType.CREATED_AT) {
                nextCursor = toCreatedAtCursorDTO(lastQuestionAnswer.getId(), lastQuestionAnswer.getCreatedAt());
            } else {
                nextCursor = toTitleCursorDTO(lastQuestionAnswer.getId(), lastQuestionAnswer.getQuestionTitle());
            }
        }

        return InterviewSetResponseDTO.InterviewSetCursorDTO.builder()
                .interviewSet(toGetInterviewSetResponse(interviewSet, questionAnswers))
                .nextCursor(nextCursor)
                .build();
    }
}

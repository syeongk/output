package com.sw.output.domain.interviewset.converter;

import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.entity.InterviewSetSortType;
import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import com.sw.output.domain.interviewset.entity.QuestionAnswerSortType;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.member.entity.Member;

import java.util.ArrayList;
import java.util.List;
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

    public static InterviewSetResponseDTO.GetInterviewSetDTO toGetInterviewSetResponse(InterviewSet interviewSet, List<QuestionAnswer> questionAnswers, Boolean isBookmarked) {
        return InterviewSetResponseDTO.GetInterviewSetDTO.builder()
                .id(interviewSet.getId())
                .parentId(interviewSet.getParent() != null ? interviewSet.getParent().getId() : null)
                .title(interviewSet.getTitle())
                .interviewCategory(interviewSet.getInterviewCategory())
                .jobCategory(interviewSet.getJobCategory())
                .nickname(interviewSet.getMember().getNickname())
                .createdAt(interviewSet.getCreatedAt())
                .isAnswerPublic(interviewSet.getIsAnswerPublic())
                .isBookmarked(isBookmarked)
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

    public static InterviewSetResponseDTO.InterviewSetCursorDTO toInterviewSetCursorResponse(InterviewSet interviewSet, List<QuestionAnswer> questionAnswers, Long cursorId, QuestionAnswerSortType questionAnswerSortType, Boolean isBookmarked) {
        return InterviewSetResponseDTO.InterviewSetCursorDTO.builder()
                .interviewSet(toGetInterviewSetResponse(interviewSet, questionAnswers, isBookmarked))
                .nextCursor(cursorId)
                .build();
    }

    public static InterviewSetResponseDTO.GetInterviewSetSummaryDTO toGetInterviewSetSummaryDTO(
            InterviewSetSummaryProjection interviewSet) {
        return InterviewSetResponseDTO.GetInterviewSetSummaryDTO.builder()
                .interviewSetId(interviewSet.getId())
                .title(interviewSet.getTitle())
                .nickname(interviewSet.getMember().getNickname())
                .bookmarkCount(interviewSet.getBookmarkCount())
                .mockCount(interviewSet.getMockCount())
                .isAnswerPublic(interviewSet.getIsAnswerPublic())
                .createdAt(interviewSet.getCreatedAt())
                .build();
    }

    public static InterviewSetResponseDTO.InterviewSetsCursorDTO toInterviewSetsCursorResponse(List<InterviewSetSummaryProjection> interviewSets, Long cursorId, InterviewSetSortType sortType) {
        return InterviewSetResponseDTO.InterviewSetsCursorDTO.builder()
                .interviewSets(interviewSets.stream()
                        .map(InterviewSetConverter::toGetInterviewSetSummaryDTO)
                        .collect(Collectors.toList()))
                .nextCursor(cursorId)
                .build();
    }
}

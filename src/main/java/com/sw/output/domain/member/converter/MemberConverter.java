package com.sw.output.domain.member.converter;

import com.sw.output.domain.interviewset.converter.InterviewSetConverter;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {
    public static Member toMember(String email, String picture) {
        String nickname = email.split("@")[0];

        return Member.builder()
                .email(email)
                .nickname(nickname)
                .picture(picture)
                .build();
    }

    public static MemberResponseDTO.GetMyPageDTO toGetMyPageResponse(Member member) {
        return MemberResponseDTO.GetMyPageDTO.builder()
                .nickname(member.getNickname())
                .picture(member.getPicture())
                .build();
    }

    public static InterviewSetResponseDTO.InterviewSetsCursorDTO toInterviewSetsCursorDTO(List<InterviewSetSummaryProjection> interviewSets, Long cursorId) {
        return InterviewSetResponseDTO.InterviewSetsCursorDTO.builder()
                .interviewSets(interviewSets.stream()
                        .map(InterviewSetConverter::toGetInterviewSetSummaryDTO)
                        .collect(Collectors.toList()))
                .nextCursor(cursorId)
                .build();

    }
}

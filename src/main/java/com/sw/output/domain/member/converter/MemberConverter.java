package com.sw.output.domain.member.converter;

import com.sw.output.domain.interviewset.converter.InterviewSetConverter;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.global.dto.CommonResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

import static com.sw.output.global.converter.CommonConverter.toCreatedAtCursorDTO;

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

    public static InterviewSetResponseDTO.InterviewSetsCursorDTO toInterviewSetsCursorDTO(List<InterviewSetSummaryProjection> interviewSets, InterviewSetSummaryProjection lastInterviewSet) {
        CommonResponseDTO.CursorDTO nextCursor = null;

        if (lastInterviewSet != null) {
            nextCursor = toCreatedAtCursorDTO(lastInterviewSet.getId(), lastInterviewSet.getCreatedAt());
        }

        return InterviewSetResponseDTO.InterviewSetsCursorDTO.builder()
                .interviewSets(interviewSets.stream()
                        .map(InterviewSetConverter::toGetInterviewSetSummaryDTO)
                        .collect(Collectors.toList()))
                .nextCursor(nextCursor)
                .build();

    }
}

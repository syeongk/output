package com.sw.output.domain.member.converter;

import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.dto.MyPageResponseDTO;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.global.dto.CommonResponseDTO;

import java.util.List;

import static com.sw.output.global.converter.CommonConverter.toCursorDTO;

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
                .build();
    }

    public static MyPageResponseDTO.GetMyInterviewSetsDTO toGetMyInterviewSetsDTO(List<InterviewSetSummaryProjection> interviewSets, InterviewSetSummaryProjection lastInterviewSet) {
        CommonResponseDTO.CursorDTO nextCursor = null;

        if (lastInterviewSet != null) {
            nextCursor = toCursorDTO(lastInterviewSet.getId(), lastInterviewSet.getCreatedAt());
        }

        return MyPageResponseDTO.GetMyInterviewSetsDTO.builder()
                .interviewSets(interviewSets)
                .cursorDTO(nextCursor)
                .build();

    }
}

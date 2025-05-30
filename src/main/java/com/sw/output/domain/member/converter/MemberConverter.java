package com.sw.output.domain.member.converter;

import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.entity.Member;

public class MemberConverter {
    public static Member toMember(String email) {
        String nickname = email.split("@")[0];

        return Member.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }

    public static MemberResponseDTO.GetMyPageDTO toGetMyPageResponse(Member member) {
        return MemberResponseDTO.GetMyPageDTO.builder()
                .nickname(member.getNickname())
                .build();
    }
}

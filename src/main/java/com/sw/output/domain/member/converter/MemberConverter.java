package com.sw.output.domain.member.converter;

import com.sw.output.domain.member.entity.Member;

public class MemberConverter {
    public static Member toMember(String email) {
        String nickname = email.split("@")[0];

        return Member.builder()
                .email(email)
                .nickname(nickname)
                .isDeleted(false)
                .build();
    }
}


package com.sw.output.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberResponseDTO {
    @Getter
    @Builder
    public static class GetMyPageDTO {
        private String nickname;
    }
}

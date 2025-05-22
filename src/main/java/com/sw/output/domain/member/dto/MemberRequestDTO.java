package com.sw.output.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class MemberRequestDTO {
    @Getter
    public static class UpdateNicknameDTO {
        @NotBlank
        @Size(max = 30)
        private String nickname;
    }
}

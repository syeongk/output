package com.sw.output.admin.bannedkeyword.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class AdminBannedKeywordRequestDTO {
    @Getter
    @Setter
    public static class BannedKeywordDTO {
        @NotBlank(message = "키워드를 입력해주세요.")
        @Size(max = 50, message = "키워드는 50자 이하로 입력해주세요.")
        private String keyword;
    }
}

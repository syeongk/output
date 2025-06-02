package com.sw.output.domain.notice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class NoticeRequestDTO {
    @Getter
    public static class NoticeDTO {
        @NotBlank(message = "제목을 입력해주세요.")
        @Size(max = 50, message = "제목은 50자 이하로 입력해주세요.")
        private String title;

        @NotBlank(message = "내용을 입력해주세요.")
        @Size(max = 3000, message = "내용은 3000자 이하로 입력해주세요.")
        private String content;
    }
}

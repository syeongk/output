package com.sw.output.domain.complaint.dto;

import com.sw.output.domain.complaint.entity.ComplaintType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class ComplaintRequestDTO {
    @Getter    
    public static class CreateComplaintDTO {
        @NotNull(message = "신고 유형을 선택해주세요.")
        private ComplaintType type;

        @NotBlank(message = "신고 내용을 입력해주세요.")
        @Size(max = 100, message = "신고 내용은 100자 이하로 입력해주세요.")
        private String content;
    }
}

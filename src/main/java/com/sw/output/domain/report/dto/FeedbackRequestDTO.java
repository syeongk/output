package com.sw.output.domain.report.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackRequestDTO {
    @NotNull(message = "피드백 ID를 입력해 주세요.")
    private Long feedbackId;
    @NotNull(message = "답변은 필수 입력 항목입니다.")
    private String memberAnswer;
}

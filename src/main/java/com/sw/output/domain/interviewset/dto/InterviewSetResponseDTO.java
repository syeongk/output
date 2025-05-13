package com.sw.output.domain.interviewset.dto;

import lombok.Builder;
import lombok.Getter;

public class InterviewSetResponseDTO {
    @Getter
    @Builder
    public static class CreateInterviewSetDTO {
        private Long interviewSetId;
    }
}

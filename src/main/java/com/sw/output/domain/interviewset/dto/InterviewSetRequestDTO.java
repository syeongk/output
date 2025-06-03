package com.sw.output.domain.interviewset.dto;

import com.sw.output.domain.interviewset.entity.InterviewCategory;
import com.sw.output.domain.interviewset.entity.JobCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

public class InterviewSetRequestDTO {
    @Getter
    public static class InterviewSetDTO {
        @NotNull(message = "면접 카테고리는 필수 항목입니다")
        private InterviewCategory interviewCategory;

        @NotNull(message = "직무 카테고리는 필수 항목입니다")
        private JobCategory jobCategory;

        @Size(min = 1, max = 50, message = "세트 제목은 1~50자 사이입니다.")
        private String title;

        @Valid
        @Size(min = 1, max = 50, message = "질문 답변은 1~50개 입니다.")
        private List<QuestionAnswerDTO> questionAnswers;

        @NotNull(message = "답변 공개 여부는 필수 항목입니다")
        private Boolean isAnswerPublic;
    }

    @Getter
    public static class CreateQuestionsPromptDTO {
        @NotNull(message = "면접 카테고리는 필수 항목입니다")
        private InterviewCategory interviewCategory;

        @NotNull(message = "직무 카테고리는 필수 항목입니다")
        private JobCategory jobCategory;

        @Size(min = 1, max = 50, message = "세트 제목은 1~50자 사이입니다.")
        private String title;

        @NotNull(message = "질문 요청 갯수는 필수 항목입니다.")
        private Integer questionCount;
    }
}

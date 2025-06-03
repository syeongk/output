package com.sw.output.domain.interviewset.dto;

import lombok.Getter;

import java.util.List;

public class OpenAIResponseDTO {
    @Getter
    public static class AIQuestionsDTO {
        private List<Choice> choices;
    }

    @Getter
    public static class Choice {
        private Integer index;
        private Message message;
    }

    @Getter
    public static class Message {
        private String role;
        private String content;
    }
}

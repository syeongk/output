package com.sw.output.domain.report.service;

import com.sw.output.domain.interviewset.dto.OpenAIResponseDTO;
import com.sw.output.domain.report.entity.Feedback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenAIService {
    private final RestTemplate openAITemplate;
    private final FeedbackService feedbackService;

    @Value("${openai.api.model}")
    private String model;

    @Async
    public void processAIFeedback(String memberAnswer, String questionTitle, Feedback feedback) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();

        String prompt = """
                면접관의 입장에서 아래 답변에 대한 피드백을 해주세요.
                추가 질문이나 답변 요청은 하지 말고, 바로 피드백만 작성하세요.
                    
                [입력 정보]
                질문 : %s
                답변 : %s
                    
                [출력 형식]
                면접관의 관점에서, 사용자의 1분 답변에 대해 실제 면접 상황을 기준으로 개선 가능한 점 3가지를 구체적으로 피드백해주세요.
                이때, 답변이 없거나 모르겠다고 응답한 경우, 답변 예시 등을 포함해 주세요.
                응답 형식1 : "1. 피드백1 \n2. 피드백2 \n3. 피드백3"
                    
                질문 또는 답변 정보가 부적절해서 피드백을 전혀 생성할 수 없는 경우
                응답 형식2 : "피드백을 드리기 어렵습니다. 질문 또는 답변을 구체적으로 입력해 주세요."
                    
                “응답 형식1 : ”, “응답 형식2 : ” 는 응답에 포함하지 마세요.
                """.formatted(
                questionTitle,
                memberAnswer);

        body.put("model", model);
        body.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        OpenAIResponseDTO.AIQuestionsDTO response = openAITemplate.postForObject(
                "https://api.openai.com/v1/chat/completions",
                request,
                OpenAIResponseDTO.AIQuestionsDTO.class);

        String feedbackContent = response.getChoices().get(0).getMessage().getContent();

        log.info(feedbackContent);

        feedbackService.updateFeedbackResult(feedback.getId(), memberAnswer, feedbackContent, prompt);
    }
}

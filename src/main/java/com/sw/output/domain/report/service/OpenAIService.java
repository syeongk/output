package com.sw.output.domain.report.service;

import com.sw.output.domain.interviewset.dto.OpenAIResponseDTO;
import com.sw.output.domain.report.dto.OpenAIDTO;
import com.sw.output.domain.report.entity.Feedback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OpenAIService {
    private final RestTemplate openAITemplate;
    private final FeedbackService feedbackService;
    @Value("${openai.api.model}")
    private String model;

    public OpenAIService(@Qualifier("openAITemplate") RestTemplate openAITemplate,
                         FeedbackService feedbackService) {
        this.openAITemplate = openAITemplate;
        this.feedbackService = feedbackService;
    }

    @Async
    public CompletableFuture<Void> processAIFeedback(String memberAnswer, String questionTitle, Feedback feedback) {
        log.info("[{}] Chat gpt", Thread.currentThread().getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();

        String prompt = """
                당신은 면접관입니다.
                다음 입력 정보는 면접에서의 질문과, 최대 1분 답변입니다.
                면접관의 관점에서 답변을 평가해 주세요.
                추가 질문이나 답변 요청은 하지 말고, 바로 피드백만 작성하세요.

                [입력 정보]
                질문 : %s
                답변 : %s

                [출력 형식]
                답변 내용 분석 : (내용 관점에서의 피드백 작성)
                답변 구조 분석: (논리 구성 및 흐름 관점의 피드백 작성)
                점수 : X점

                - 질문과 답변이 실제 면접 상황과 1분 답변이라는 점을 고려해, 피드백은 답변의 장점과 필요한 개선점을 균형 있게 작성해주세요.
                - 사소한 표현 차이는 제외하되, 개념적 오류나 논리적 오류는 반드시 피드백에 포함시켜 주세요.
                - 완성도 높은 답변은 적극적으로 5점을 주세요. 답변이 명확하고 설득력이 충분하다면, 긍정적인 피드백 중심으로 작성해도 됩니다.
                - 점수는 1점에서 5점 사이의 정수로 평가해주세요.
                  4~5점: 우수, 핵심 내용과 구조 모두 명확하고 설득력 있으며, 실전 면접에서도 긍정적으로 평가될 수 있음. 긍정적인 피드백 중심 작성 가능.
                  3점: 보통, 내용은 적절하지만, 일부 설명 부족 또는 보완이 필요한 부분이 있음. 개선점 중심 피드백 필요.
                  1~2점 : 미흡, 전반적으로 핵심 전달이 부족하거나 논리적 흐름이 약함. 실전에서 부정적 평가 가능. 구체적 개선 피드백 제공 필요.
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

        return CompletableFuture.completedFuture(null);
    }

    @Async
    public void processAudioAndFeedback(MultipartFile audioFile, String questionTitle, Feedback feedback) {
        sttMemberAnswer(audioFile)
                .thenCompose(memberAnswer ->
                        processAIFeedback(memberAnswer, questionTitle, feedback)
                );
    }

    @Async
    public CompletableFuture<String> sttMemberAnswer(MultipartFile audioFile) {
        log.info("[{}] STT", Thread.currentThread().getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", audioFile.getResource());
        body.add("model", "whisper-1");
        body.add("language", "ko");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        OpenAIDTO.WhisperResponseDTO response = openAITemplate.postForObject(
                "https://api.openai.com/v1/audio/transcriptions",
                request,
                OpenAIDTO.WhisperResponseDTO.class);

        return CompletableFuture.completedFuture(response.getText());
    }

}

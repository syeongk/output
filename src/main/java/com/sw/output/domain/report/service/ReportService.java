package com.sw.output.domain.report.service;

import com.sw.output.domain.interviewset.dto.OpenAIResponseDTO;
import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import com.sw.output.domain.interviewset.repository.QuestionAnswerRepository;
import com.sw.output.domain.report.converter.ReportDTOConverter;
import com.sw.output.domain.report.dto.ReportRequestDTO;
import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.Report;
import com.sw.output.domain.report.repository.FeedbackRepository;
import com.sw.output.domain.report.repository.ReportRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import com.sw.output.global.response.errorcode.ReportErrorCode;
import jakarta.transaction.Transactional;
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
import java.util.stream.Collectors;

import static com.sw.output.domain.report.converter.FeedbackConverter.toFeedback;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final RestTemplate openAITemplate;
    private final FeedbackRepository feedbackRepository;

    @Value("${openai.api.model}")
    private String model;

    @Async
    @Transactional
    public void createAIFeedback(Long reportId, ReportRequestDTO.CreateAiFeedbackDTO createAiFeedbackDTO) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessException(ReportErrorCode.REPORT_NOT_FOUND));

        QuestionAnswer questionAnswer = questionAnswerRepository.findByInterviewSetIdAndQuestionTitle(report.getInterviewSet().getId(), createAiFeedbackDTO.getQuestionTitle())
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.QUESTION_ANSWER_NOT_FOUND));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();

        String prompt = """
                당신은 면접관입니다. 면접관의 입장에서 아래 질문에 대한 답변을 피드백 해주세요.

                [입력 정보]
                질문 : %s
                답변 : %s

                [출력 형식]
                질문에 대한 답변을 구체적으로 3가지 피드백해주세요.
                응답 예시 : "1. 피드백1 \n2. 피드백2 \n3. 피드백3"
                """.formatted(
                createAiFeedbackDTO.getQuestionTitle(),
                createAiFeedbackDTO.getMemberAnswer());

        body.put("model", model);
        body.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        OpenAIResponseDTO.AIQuestionsDTO response = openAITemplate.postForObject(
                "https://api.openai.com/v1/chat/completions",
                request,
                OpenAIResponseDTO.AIQuestionsDTO.class);

        log.info(response.getChoices().get(0).getMessage().getContent());

        String feedbackContent = response.getChoices().get(0).getMessage().getContent();

        Feedback feedback = toFeedback(report, questionAnswer, createAiFeedbackDTO.getMemberAnswer(), feedbackContent, prompt);
        feedbackRepository.save(feedback);
    }

    @Transactional
    public void deleteReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessException(ReportErrorCode.REPORT_NOT_FOUND));

        if (report.getIsDeleted()) {
            throw new BusinessException(ReportErrorCode.REPORT_ALREADY_DELETED);
        }

        report.softDelete();
    }

    public List<ReportResponseDTO.GetReportDTO> getReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessException(ReportErrorCode.REPORT_NOT_FOUND));

        if (report.getIsDeleted()) {
            throw new BusinessException(ReportErrorCode.REPORT_NOT_FOUND);
        }

        return report.getFeedbacks().stream()
                .map(ReportDTOConverter::toGetReportDTO)
                .collect(Collectors.toList());
    }


}

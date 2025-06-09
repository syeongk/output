package com.sw.output.domain.report.service;

import com.sw.output.domain.interviewset.dto.OpenAIResponseDTO;
import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import com.sw.output.domain.interviewset.repository.QuestionAnswerRepository;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.domain.report.dto.FeedbackResponseDTO;
import com.sw.output.domain.report.dto.ReportRequestDTO;
import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.Report;
import com.sw.output.domain.report.repository.FeedbackRepository;
import com.sw.output.domain.report.repository.ReportRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import com.sw.output.global.response.errorcode.MemberErrorCode;
import com.sw.output.global.response.errorcode.ReportErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sw.output.domain.report.converter.FeedbackConverter.toFeedback;
import static com.sw.output.domain.report.converter.FeedbackDTOConverter.toFeedbacksDTO;
import static com.sw.output.global.util.SecurityUtils.getAuthenticatedUsername;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final RestTemplate openAITemplate;
    private final FeedbackRepository feedbackRepository;
    private final MemberRepository memberRepository;

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
                면접관의 입장에서 아래 답변에 대한 피드백을 해주세요.
                추가 질문이나 답변 요청은 하지 말고, 바로 피드백만 작성하세요.

                [입력 정보]
                질문 : %s
                답변 : %s

                [출력 형식]
                반드시 아래 형식으로 3가지 구체적인 피드백을 해주세요.
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

    public FeedbackResponseDTO.FeedbacksDTO getReport(Long reportId, Long cursorId, LocalDateTime cursorCreatedAt, int pageSize) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Report report = reportRepository.findByIdAndMemberId(reportId, member.getId())
                .orElseThrow(() -> new BusinessException(ReportErrorCode.REPORT_NOT_FOUND));

        if (report.getIsDeleted()) {
            throw new BusinessException(ReportErrorCode.REPORT_NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<Feedback> feedbackSlice;
        if (cursorId == null || cursorCreatedAt == null) {
            feedbackSlice = feedbackRepository.findFeedbacksFirstPage(pageable, reportId);
        } else {
            feedbackSlice = feedbackRepository.findFeedbacksNextPage(pageable, reportId, cursorId, cursorCreatedAt);
        }

        List<Feedback> feedbacks = feedbackSlice.getContent();

        if (!feedbackSlice.hasNext()) {
            return toFeedbacksDTO(feedbacks, null);
        }

        Feedback lastFeedback = feedbacks.get(feedbacks.size() - 1);
        return toFeedbacksDTO(feedbacks, lastFeedback);
    }
}

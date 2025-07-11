package com.sw.output.domain.report.service;

import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import com.sw.output.domain.interviewset.repository.QuestionAnswerRepository;
import com.sw.output.domain.report.dto.FeedbackRequestDTO;
import com.sw.output.domain.report.dto.OpenAIDTO;
import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.FeedbackStatus;
import com.sw.output.domain.report.entity.Report;
import com.sw.output.domain.report.repository.FeedbackRepository;
import com.sw.output.domain.report.repository.ReportRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.FeedbackErrorCode;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import com.sw.output.global.response.errorcode.ReportErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.sw.output.domain.report.converter.FeedbackConverter.toFeedback;
import static com.sw.output.domain.report.converter.OpenAIDTOConverter.toAiFeedbackDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackUseCase {
    private final QuestionAnswerRepository questionAnswerRepository;
    private final OpenAIService openAIService;
    private final ReportRepository reportRepository;
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public OpenAIDTO.AiFeedbackDTO prepareAIFeedback(Long reportId, Long questionAnswerId) {
        log.info("[{}] Prepare", Thread.currentThread().getName());
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessException(ReportErrorCode.REPORT_NOT_FOUND));

        QuestionAnswer questionAnswer = questionAnswerRepository.findByIdAndInterviewSetId(
                        questionAnswerId,
                        report.getInterviewSet().getId())
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.QUESTION_ANSWER_NOT_FOUND));

        // 기존 피드백이 있는지 확인
        Optional<Feedback> existingFeedback = feedbackRepository.findByReportIdAndQuestionAnswerId(reportId,
                questionAnswerId);
        Feedback feedback;
        if (existingFeedback.isPresent()) {
            // 기존 피드백이 있으면 상태 업데이트
            feedback = existingFeedback.get();
            feedback.updateFeedbackStatus(FeedbackStatus.PROCESSING);
        } else {
            // 새로운 피드백 생성
            feedback = toFeedback(report, questionAnswer, null, null, null);
            feedbackRepository.save(feedback);
        }

        return toAiFeedbackDTO(report, questionAnswer, feedback);
    }

    private OpenAIDTO.AiFeedbackDTO prepareAIFeedback(Long reportId, Long feedbackId, String memberAnswer) {
        log.info("[{}] Prepare", Thread.currentThread().getName());

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessException(ReportErrorCode.REPORT_NOT_FOUND));

        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new BusinessException(FeedbackErrorCode.FEEDBACK_NOT_FOUND));
        feedback.updateFeedbackStatus(FeedbackStatus.PROCESSING);
        feedback.updateMemberAnswer(memberAnswer);

        QuestionAnswer questionAnswer = feedback.getQuestionAnswer();

        return toAiFeedbackDTO(report, questionAnswer, feedback);
    }

    @Transactional
    public void createAIFeedback(Long reportId, Long questionAnswerId, MultipartFile audioFile) {
        log.info("[{}] Start", Thread.currentThread().getName());
        OpenAIDTO.AiFeedbackDTO aiFeedbackDTO = prepareAIFeedback(reportId, questionAnswerId);

        openAIService.processAudioAndFeedback(audioFile, aiFeedbackDTO.getQuestionAnswer().getQuestionTitle(), aiFeedbackDTO.getFeedback());
    }

    @Transactional
    public void recreateAIFeedback(Long reportId, FeedbackRequestDTO recreateAiFeedbackDTO) {
        log.info("[{}] Start", Thread.currentThread().getName());
        OpenAIDTO.AiFeedbackDTO aiFeedbackDTO = prepareAIFeedback(reportId, recreateAiFeedbackDTO.getFeedbackId(), recreateAiFeedbackDTO.getMemberAnswer());

        openAIService.processAIFeedback(recreateAiFeedbackDTO.getMemberAnswer(),
                aiFeedbackDTO.getQuestionAnswer().getQuestionTitle(), aiFeedbackDTO.getFeedback());
    }
}

package com.sw.output.domain.report.service;

import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.domain.report.dto.FeedbackResponseDTO;
import com.sw.output.domain.report.entity.Feedback;
import com.sw.output.domain.report.entity.Report;
import com.sw.output.domain.report.repository.FeedbackRepository;
import com.sw.output.domain.report.repository.ReportRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.FeedbackErrorCode;
import com.sw.output.global.response.errorcode.MemberErrorCode;
import com.sw.output.global.response.errorcode.ReportErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sw.output.domain.report.converter.FeedbackDTOConverter.toFeedbacksDTO;
import static com.sw.output.global.util.SecurityUtils.getAuthenticatedUsername;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    private final FeedbackRepository feedbackRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void deleteReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessException(ReportErrorCode.REPORT_NOT_FOUND));

        if (report.getIsDeleted()) {
            throw new BusinessException(ReportErrorCode.REPORT_ALREADY_DELETED);
        }

        report.softDelete();
    }

    public FeedbackResponseDTO.FeedbacksDTO getReport(Long reportId, Long cursorId, int pageSize) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Report report = reportRepository.findByIdAndMemberId(reportId, member.getId())
                .orElseThrow(() -> new BusinessException(ReportErrorCode.REPORT_NOT_FOUND));

        if (report.getIsDeleted()) {
            throw new BusinessException(ReportErrorCode.REPORT_NOT_FOUND);
        }

        Feedback feedback = null;
        if (cursorId != null) {
            feedback = feedbackRepository.findById(cursorId)
                    .orElseThrow(() -> new BusinessException(FeedbackErrorCode.FEEDBACK_NOT_FOUND));
        }

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<Feedback> feedbackSlice;
        if (cursorId == null) {
            feedbackSlice = feedbackRepository.findFeedbacksFirstPage(pageable, reportId);
        } else {
            feedbackSlice = feedbackRepository.findFeedbacksNextPage(pageable, reportId, feedback.getId(), feedback.getCreatedAt());
        }

        List<Feedback> feedbacks = feedbackSlice.getContent();

        if (!feedbackSlice.hasNext()) {
            return toFeedbacksDTO(feedbacks, null);
        }

        Feedback lastFeedback = feedbacks.get(feedbacks.size() - 1);
        return toFeedbacksDTO(feedbacks, lastFeedback.getId());
    }
}

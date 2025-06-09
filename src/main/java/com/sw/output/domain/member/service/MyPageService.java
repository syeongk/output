package com.sw.output.domain.member.service;

import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import com.sw.output.domain.member.converter.MemberConverter;
import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.dto.MyPageResponseDTO;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.domain.report.entity.Report;
import com.sw.output.domain.report.repository.ReportRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sw.output.domain.member.converter.MemberConverter.toGetMyInterviewSetsDTO;
import static com.sw.output.domain.member.converter.MyPageConverter.toBookmarkedInterviewSetsDTO;
import static com.sw.output.domain.report.converter.ReportDTOConverter.toReportsDTO;
import static com.sw.output.global.util.SecurityUtils.getAuthenticatedUsername;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final InterviewSetRepository interviewSetRepository;
    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;

    /**
     * 마이페이지 조회
     *
     * @return 마이페이지 조회 응답 (닉네임)
     * @throws BusinessException 회원을 찾을 수 없는 경우
     */
    public MemberResponseDTO.GetMyPageDTO getMyPage() {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberConverter.toGetMyPageResponse(member);
    }

    /**
     * 북마크한 면접 세트 목록 조회
     *
     * @return 북마크한 면접 세트 목록
     */
    public MyPageResponseDTO.BookmarkedInterviewSetsDTO getMyBookmarkedInterviewSets(Long cursorId, LocalDateTime cursorCreatedAt, int pageSize) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<InterviewSetSummaryProjection> bookmarkedInterviewSetSlice;
        if (cursorId == null || cursorCreatedAt == null) {
            bookmarkedInterviewSetSlice = interviewSetRepository.findBookmarkedInterviewSetsFirstPage(pageable, member.getId());
        } else {
            bookmarkedInterviewSetSlice = interviewSetRepository.findBookmarkedInterviewSetsNextPage(pageable, member.getId(), cursorId, cursorCreatedAt);
        }

        if (bookmarkedInterviewSetSlice.isEmpty()) {
            return toBookmarkedInterviewSetsDTO(new ArrayList<>(), null);
        }

        List<InterviewSetSummaryProjection> interviewSets = bookmarkedInterviewSetSlice.getContent();
        if (!bookmarkedInterviewSetSlice.hasNext()) {
            return toBookmarkedInterviewSetsDTO(interviewSets, null);
        } else {
            InterviewSetSummaryProjection lastInterviewSet = interviewSets.get(interviewSets.size() - 1);
            return toBookmarkedInterviewSetsDTO(interviewSets, lastInterviewSet);
        }
    }

    /**
     * 사용자 면접 세트 목록 조회
     *
     * @return 사용자 면접 세트 목록
     */
    public MyPageResponseDTO.MyInterviewSetsDTO getMyInterviewSets(Long cursorId, LocalDateTime cursorCreatedAt, int pageSize) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<InterviewSetSummaryProjection> interviewSetSlice;
        if (cursorId == null || cursorCreatedAt == null) {
            interviewSetSlice = interviewSetRepository.findMyInterviewSetsFirstPage(pageable, member.getId());
        } else {
            interviewSetSlice = interviewSetRepository.findMyInterviewSetsNextPage(pageable, cursorId, cursorCreatedAt, member.getId());
        }

        if (interviewSetSlice.isEmpty()) {
            return toGetMyInterviewSetsDTO(new ArrayList<>(), null);
        }

        List<InterviewSetSummaryProjection> interviewSets = interviewSetSlice.getContent();
        if (!interviewSetSlice.hasNext()) {
            return toGetMyInterviewSetsDTO(interviewSets, null);
        } else {
            InterviewSetSummaryProjection lastInterviewSet = interviewSets.get(interviewSets.size() - 1);
            return toGetMyInterviewSetsDTO(interviewSets, lastInterviewSet);
        }
    }

    public ReportResponseDTO.ReportsDTO getMyReports(Long cursorId, LocalDateTime cursorCreatedAt, int pageSize) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<Report> reportSlice;
        if (cursorId == null || cursorCreatedAt == null) {
            reportSlice = reportRepository.findReportFirstPage(pageable, member.getId());
        } else {
            reportSlice = reportRepository.findReportNextPage(pageable, member.getId(), cursorId, cursorCreatedAt);
        }

        if (reportSlice.isEmpty()) {
            return toReportsDTO(new ArrayList<>(), null);
        }

        List<Report> reports = reportSlice.getContent();
        if (!reportSlice.hasNext()) {
            return toReportsDTO(reports, null);
        } else {
            Report lastReport = reports.get(reports.size() - 1);
            return toReportsDTO(reports, lastReport);
        }
    }
}

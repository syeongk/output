package com.sw.output.domain.member.service;

import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.entity.Bookmark;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.interviewset.repository.BookmarkRepository;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import com.sw.output.domain.member.converter.MemberConverter;
import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.domain.report.dto.ReportResponseDTO;
import com.sw.output.domain.report.entity.Report;
import com.sw.output.domain.report.repository.ReportRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.BookmarkErrorCode;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import com.sw.output.global.response.errorcode.MemberErrorCode;
import com.sw.output.global.response.errorcode.ReportErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sw.output.domain.member.converter.MemberConverter.toInterviewSetsCursorDTO;
import static com.sw.output.domain.report.converter.ReportDTOConverter.toReportsDTO;
import static com.sw.output.global.util.SecurityUtils.getAuthenticatedUsername;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final InterviewSetRepository interviewSetRepository;
    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;
    private final BookmarkRepository bookmarkRepository;

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
    public InterviewSetResponseDTO.InterviewSetsCursorDTO getMyBookmarkedInterviewSets(Long cursorId, int pageSize) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Bookmark bookmark = null;
        if (cursorId != null) {
            bookmark = bookmarkRepository.findById(cursorId)
                    .orElseThrow(() -> new BusinessException(BookmarkErrorCode.BOOKMARK_NOT_FOUND));
        }

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<InterviewSetSummaryProjection> bookmarkedInterviewSetSlice;
        if (cursorId == null) {
            bookmarkedInterviewSetSlice = interviewSetRepository.findBookmarkedInterviewSetsFirstPage(pageable, member.getId());
        } else {
            bookmarkedInterviewSetSlice = interviewSetRepository.findBookmarkedInterviewSetsNextPage(pageable, member.getId(), bookmark.getId(), bookmark.getCreatedAt());
        }

        if (bookmarkedInterviewSetSlice.isEmpty()) {
            return toInterviewSetsCursorDTO(new ArrayList<>(), null);
        }

        List<InterviewSetSummaryProjection> bookmarkedInterviewSets = bookmarkedInterviewSetSlice.getContent();
        if (!bookmarkedInterviewSetSlice.hasNext()) {
            return toInterviewSetsCursorDTO(bookmarkedInterviewSets, null);
        } else {
            InterviewSetSummaryProjection lastInterviewSet = bookmarkedInterviewSets.get(bookmarkedInterviewSets.size() - 1);
            return toInterviewSetsCursorDTO(bookmarkedInterviewSets, lastInterviewSet.getBookmark().getId());
        }
    }

    /**
     * 사용자 면접 세트 목록 조회
     *
     * @return 사용자 면접 세트 목록
     */
    public InterviewSetResponseDTO.InterviewSetsCursorDTO getMyInterviewSets(Long cursorId, int pageSize) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        InterviewSet interviewSet = null;
        if (cursorId != null) {
            interviewSet = interviewSetRepository.findById(cursorId)
                    .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));
        }

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<InterviewSetSummaryProjection> interviewSetSlice;
        if (cursorId == null) {
            interviewSetSlice = interviewSetRepository.findMyInterviewSetsFirstPage(pageable, member.getId());
        } else {
            interviewSetSlice = interviewSetRepository.findMyInterviewSetsNextPage(pageable, interviewSet.getId(), interviewSet.getCreatedAt(), member.getId());
        }

        if (interviewSetSlice.isEmpty()) {
            return toInterviewSetsCursorDTO(new ArrayList<>(), null);
        }

        List<InterviewSetSummaryProjection> interviewSets = interviewSetSlice.getContent();
        if (!interviewSetSlice.hasNext()) {
            return toInterviewSetsCursorDTO(interviewSets, null);
        } else {
            InterviewSetSummaryProjection lastInterviewSet = interviewSets.get(interviewSets.size() - 1);
            return toInterviewSetsCursorDTO(interviewSets, lastInterviewSet.getId());
        }
    }

    /**
     * 사용자 리포트 목록 조회
     *
     * @param cursorId 커서 ID
     * @param pageSize 페이지 크기
     * @return 사용자 리포트 목록
     */
    public ReportResponseDTO.ReportsDTO getMyReports(Long cursorId, int pageSize) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Report report = null;
        if (cursorId != null) {
            report = reportRepository.findById(cursorId)
                    .orElseThrow(() -> new BusinessException(ReportErrorCode.REPORT_NOT_FOUND));
        }

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<Report> reportSlice;
        if (cursorId == null) {
            reportSlice = reportRepository.findReportFirstPage(pageable, member.getId());
        } else {
            reportSlice = reportRepository.findReportNextPage(pageable, member.getId(), report.getId(), report.getCreatedAt());
        }

        if (reportSlice.isEmpty()) {
            return toReportsDTO(new ArrayList<>(), null);
        }

        List<Report> reports = reportSlice.getContent();
        if (!reportSlice.hasNext()) {
            return toReportsDTO(reports, null);
        } else {
            Report lastReport = reports.get(reports.size() - 1);
            return toReportsDTO(reports, lastReport.getId());
        }
    }
}

package com.sw.output.domain.member.service;

import com.sw.output.domain.interviewset.entity.Bookmark;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.interviewset.repository.BookmarkRepository;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import com.sw.output.domain.member.converter.MemberConverter;
import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.dto.MyPageResponseDTO;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.domain.report.converter.ReportDTOConverter;
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
import java.util.List;
import java.util.stream.Collectors;

import static com.sw.output.domain.member.converter.MemberConverter.toGetMyInterviewSetsDTO;
import static com.sw.output.global.util.SecurityUtils.getAuthenticatedUsername;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final BookmarkRepository bookmarkRepository;
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
    public List<InterviewSetSummaryProjection> getMyBookmarkedInterviewSets(Long cursorId, LocalDateTime cursorCreatedAt, int pageSize) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(0, pageSize);

        List<Bookmark> bookmarks = bookmarkRepository.findByMemberIdOrderByCreatedAtDesc(member.getId());

        return bookmarks.stream()
                .map(bookmark -> interviewSetRepository
                        .findSummaryByIdAndIsDeletedFalse(bookmark.getInterviewSet().getId()))
                .collect(Collectors.toList());
    }

    /**
     * 사용자 면접 세트 목록 조회
     *
     * @return 사용자 면접 세트 목록
     */
    public MyPageResponseDTO.GetMyInterviewSetsDTO getMyInterviewSets(Long cursorId, LocalDateTime cursorCreatedAt, int pageSize) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<InterviewSetSummaryProjection> interviewSetSlice;
        if (cursorId == null || cursorCreatedAt == null) {
            interviewSetSlice = interviewSetRepository.findMyInterviewSetsFirstPage(pageable, member.getId());
        } else {
            interviewSetSlice = interviewSetRepository.findMyInterviewSetsNextPage(pageable, cursorId, cursorCreatedAt, member.getId());
        }

        List<InterviewSetSummaryProjection> interviewSets = interviewSetSlice.getContent();
        InterviewSetSummaryProjection lastInterviewSet = interviewSets.get(interviewSets.size() - 1);

        if (!interviewSetSlice.hasNext()) {
            return toGetMyInterviewSetsDTO(interviewSets, null);
        } else {
            return toGetMyInterviewSetsDTO(interviewSets, lastInterviewSet);
        }
    }

    public List<ReportResponseDTO.GetReportDTO> getMyReports(Long cursorId, LocalDateTime cursorCreatedAt, int pageSize) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(0, pageSize);

        List<Report> reports = reportRepository.findMyReports(member.getId());

        return reports.stream()
                .map(ReportDTOConverter::toGetReportDTO)
                .collect(Collectors.toList());
    }
}

package com.sw.output.domain.member.service;

import com.sw.output.domain.interviewset.entity.Bookmark;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.interviewset.repository.BookmarkRepository;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import com.sw.output.domain.member.converter.MemberConverter;
import com.sw.output.domain.member.dto.MemberResponseDTO;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final BookmarkRepository bookmarkRepository;
    private final InterviewSetRepository interviewSetRepository;
    private final MemberRepository memberRepository;

    /**
     * 북마크한 면접 세트 목록 조회
     *
     * @return 북마크한 면접 세트 목록
     */
    public List<InterviewSetSummaryProjection> getBookmarkedInterviewSets() {
        // TODO : 멤버 조회 AOP 추가, 1번으로 하드코딩
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        List<Bookmark> bookmarks = bookmarkRepository.findByMemberIdOrderByCreatedAtDesc(1L);

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
    public List<InterviewSetSummaryProjection> getMyInterviewSets() {
        // TODO : 멤버 조회 AOP 추가, 1번으로 하드코딩
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        List<InterviewSetSummaryProjection> interviewSets = interviewSetRepository
                .findByMemberIdAndIsDeletedFalseOrderByCreatedAtDesc(1L);

        return interviewSets;
    }

    /**
     * 마이페이지 조회
     *
     * @return 마이페이지 조회 응답 (닉네임)
     * @throws BusinessException 회원을 찾을 수 없는 경우
     */
    public MemberResponseDTO.GetMyPageDTO getMyPage() {
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberConverter.toGetMyPageResponse(member);
    }
}

package com.sw.output.domain.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sw.output.domain.interviewset.entity.Bookmark;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.interviewset.repository.BookmarkRepository;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import com.sw.output.domain.member.dto.MemberRequestDTO;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.MemberErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

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
        List<Bookmark> bookmarks = bookmarkRepository.findByMemberIdOrderByCreatedAtDesc(1L);

        return bookmarks.stream()
                .map(bookmark -> interviewSetRepository.findSummaryById(bookmark.getInterviewSet().getId()))
                .collect(Collectors.toList());
    }

    /**
     * 닉네임 수정
     * 
     * @param request 닉네임 수정 요청
     * @throws BusinessException 회원을 찾을 수 없거나, 닉네임이 이미 존재할 경우
     */
    @Transactional
    public void updateNickname(MemberRequestDTO.UpdateNicknameDTO updateNicknameDTO) {
        String nickname = updateNicknameDTO.getNickname();

        // TODO : 멤버 조회 AOP 추가, 1번으로 하드코딩
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (memberRepository.findByNickname(nickname).isPresent()) {
            throw new BusinessException(MemberErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        member.updateNickname(nickname);
    }

    /**
     * 사용자 면접 세트 목록 조회
     * 
     * @return 사용자 면접 세트 목록
     */
    public List<InterviewSetSummaryProjection> getMyInterviewSets() {
        // TODO : 멤버 조회 AOP 추가, 1번으로 하드코딩
        List<InterviewSetSummaryProjection> interviewSets = interviewSetRepository
                .findByMemberIdOrderByCreatedAtDesc(1L);

        return interviewSets;
    }
}

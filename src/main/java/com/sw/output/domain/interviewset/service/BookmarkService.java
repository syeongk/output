package com.sw.output.domain.interviewset.service;

import com.sw.output.domain.interviewset.entity.Bookmark;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.repository.BookmarkRepository;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.BookmarkErrorCode;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import com.sw.output.global.response.errorcode.MemberErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sw.output.domain.interviewset.converter.BookmarkConverter.toBookmark;
import static com.sw.output.global.util.SecurityUtils.getAuthenticatedUsername;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final InterviewSetRepository interviewSetRepository;
    private final MemberRepository memberRepository;

    /**
     * 면접 세트를 북마크합니다.
     *
     * @param interviewSetId 북마크할 면접 세트 ID
     * @throws BusinessException 면접 세트가 존재하지 않는 경우, 멤버가 존재하지 않는 경우
     */
    @Transactional
    public void createBookmark(Long interviewSetId) {
        if (bookmarkRepository.findByInterviewSetIdAndMemberId(interviewSetId, 1L).isPresent()) {
            throw new BusinessException(BookmarkErrorCode.ALREADY_BOOKMARKED);
        }

        InterviewSet interviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));

        if (interviewSet.getIsDeleted()) {
            throw new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_DELETED);
        }

        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Bookmark bookmark = toBookmark(interviewSet, member);
        bookmarkRepository.save(bookmark);

        // TODO : 동시성 문제 해결 필요
        interviewSet.addBookmarkCount();
    }

    /**
     * 면접 세트 북마크를 삭제합니다.
     *
     * @param interviewSetId 삭제할 면접 세트 ID
     * @throws BusinessException 면접 세트가 존재하지 않는 경우, 북마크가 존재하지 않는 경우
     */
    @Transactional
    public void deleteBookmark(Long interviewSetId) {
        InterviewSet interviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));

        if (interviewSet.getIsDeleted()) {
            throw new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_DELETED);
        }

        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Bookmark bookmark = bookmarkRepository.findByInterviewSetIdAndMemberId(interviewSetId, member.getId())
                .orElseThrow(() -> new BusinessException(BookmarkErrorCode.BOOKMARK_NOT_FOUND));

        bookmarkRepository.delete(bookmark);

        // TODO : 동시성 문제 해결 필요
        interviewSet.subtractBookmarkCount();
    }
}

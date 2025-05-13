package com.sw.output.domain.member.service;

import com.sw.output.domain.interviewset.entity.Bookmark;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.interviewset.repository.BookmarkRepository;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final BookmarkRepository bookmarkRepository;
    private final InterviewSetRepository interviewSetRepository;

    public List<InterviewSetSummaryProjection> getBookmarkedInterviewSets() {
        // TODO : 멤버 조회 AOP 추가, 1번으로 하드코딩
        List<Bookmark> bookmarks = bookmarkRepository.findByMemberIdOrderByCreatedAtDesc(1L);

        return bookmarks.stream()
                .map(bookmark -> interviewSetRepository.findSummaryById(bookmark.getInterviewSet().getId()))
                .collect(Collectors.toList());
    }
}

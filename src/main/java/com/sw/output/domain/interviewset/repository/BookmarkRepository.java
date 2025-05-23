package com.sw.output.domain.interviewset.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sw.output.domain.interviewset.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByInterviewSetIdAndMemberId(Long interviewSetId, Long memberId);

    List<Bookmark> findByMemberIdOrderByCreatedAtDesc(Long memberId);

    void deleteByInterviewSetId(Long interviewSetId);
}

package com.sw.output.domain.interviewset.repository;

import com.sw.output.domain.interviewset.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByInterviewSetIdAndMemberId(Long interviewSetId, Long memberId);
}

package com.sw.output.domain.report.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sw.output.domain.report.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("""
            SELECT f
            FROM Feedback f
            WHERE f.report.id = :reportId AND f.isDeleted = false
            ORDER BY f.createdAt ASC, f.id DESC
            """)
    Slice<Feedback> findFeedbacksFirstPage(
        Pageable pageable,
        @Param("reportId") Long reportId);

    @Query("""
            SELECT f
            FROM Feedback f
            WHERE f.report.id = :reportId AND f.isDeleted = false AND (f.createdAt > :cursorCreatedAt OR (f.createdAt = :cursorCreatedAt AND f.id < :cursorId))
            ORDER BY f.createdAt ASC, f.id DESC
            """)
    Slice<Feedback> findFeedbacksNextPage(
        Pageable pageable,
        @Param("reportId") Long reportId,
        @Param("cursorId") Long cursorId,
        @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt);
}

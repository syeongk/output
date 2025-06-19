package com.sw.output.domain.report.repository;

import com.sw.output.domain.report.entity.Feedback;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    Optional<Feedback> findByReportIdAndQuestionAnswerId(Long reportId, Long questionAnswerId);

    List<Feedback> findAllByReportId(Long reportId);
}

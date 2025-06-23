package com.sw.output.domain.report.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sw.output.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByIdAndMemberId(Long reportId, Long memberId);

    @Query("""
        SELECT r
        FROM Report r
        WHERE r.member.id = :memberId AND r.isDeleted = false
        ORDER BY r.createdAt DESC, r.id DESC
        """)
    Slice<Report> findReportFirstPage(
        Pageable pageable,
        @Param("memberId") Long memberId);

    @Query("""
        SELECT r
        FROM Report r
        WHERE r.member.id = :memberId AND r.isDeleted = false AND (r.createdAt < :cursorCreatedAt OR (r.createdAt = :cursorCreatedAt AND r.id < :cursorId))
        ORDER BY r.createdAt DESC, r.id DESC
        """)
    Slice<Report> findReportNextPage(
        Pageable pageable,
        @Param("memberId") Long memberId,
        @Param("cursorId") Long cursorId,
        @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt);

    List<Report> findByMemberId(Long memberId);

    List<Report> findByInterviewSetId(Long interviewSetId);
}

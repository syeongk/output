package com.sw.output.domain.interviewset.repository;

import com.sw.output.domain.interviewset.entity.InterviewCategory;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.entity.JobCategory;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface InterviewSetRepository extends JpaRepository<InterviewSet, Long> {
    InterviewSetSummaryProjection findSummaryByIdAndIsDeletedFalse(Long id);

    List<InterviewSetSummaryProjection> findByMemberIdAndIsDeletedFalseOrderByCreatedAtDesc(Long memberId);

    @Query("""
            SELECT DISTINCT
                is.id as id,
                is.title as title,
                is.bookmarkCount as bookmarkCount,
                is.createdAt as createdAt,
                is.isAnswerPublic as isAnswerPublic,
                m as member,
                is.mockCount as mockCount
            FROM
                InterviewSet is
                JOIN is.member m
                JOIN is.questionAnswers qa
            WHERE
                (:jobCategory IS NULL OR is.jobCategory = :jobCategory)
                AND (:interviewCategory IS NULL OR is.interviewCategory = :interviewCategory)
                AND (:keyword IS NULL OR is.title LIKE %:keyword% OR qa.questionTitle LIKE %:keyword%)
                AND (is.isDeleted = false)
            ORDER BY
                CASE :sortType WHEN 'RECOMMEND' THEN is.mockCount END DESC,
                CASE :sortType WHEN 'RECOMMEND' THEN is.bookmarkCount END DESC,
                CASE :sortType WHEN 'RECOMMEND' THEN is.id END DESC,
                CASE :sortType WHEN 'LATEST' THEN is.createdAt END DESC,
                CASE :sortType WHEN 'LATEST' THEN is.id END DESC,
                CASE :sortType WHEN 'BOOKMARK' THEN is.bookmarkCount END DESC,
                CASE :sortType WHEN 'BOOKMARK' THEN is.id END DESC
            """)
    List<InterviewSetSummaryProjection> findInterviewSets(
            @Param("jobCategory") JobCategory jobCategory,
            @Param("interviewCategory") InterviewCategory interviewCategory,
            @Param("keyword") String keyword,
            @Param("sortType") String sortType,
            @Param("size") int size,
            @Param("cursor") int cursor);

    @Query("""
            SELECT i
            FROM InterviewSet i
            WHERE i.member.id = :memberId AND i.isDeleted = false
            ORDER BY i.createdAt DESC, i.id DESC
            """)
    Slice<InterviewSetSummaryProjection> findMyInterviewSetsFirstPage(Pageable pageable, @Param("memberId") Long memberId);

    @Query("""
            SELECT i
            FROM InterviewSet i
            WHERE i.member.id = :memberId AND i.isDeleted = false AND (i.createdAt < :cursorCreatedAt OR (i.createdAt = :cursorCreatedAt AND i.id < :cursorId))
            ORDER BY i.createdAt DESC, i.id DESC
            """)
    Slice<InterviewSetSummaryProjection> findMyInterviewSetsNextPage(Pageable pageable, @Param("cursorId") Long cursorId, @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt, @Param("memberId") Long memberId);
}

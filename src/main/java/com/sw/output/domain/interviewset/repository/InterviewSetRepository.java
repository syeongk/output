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
    @Query("""
            SELECT DISTINCT
                i.id as id,
                i.title as title,
                i.bookmarkCount as bookmarkCount,
                i.mockCount as mockCount,
                i.createdAt as createdAt,
                i.isAnswerPublic as isAnswerPublic,
                m as member
            FROM
                InterviewSet i
                JOIN i.member m
                JOIN i.questionAnswers qa
            WHERE
                (:jobCategory IS NULL OR i.jobCategory = :jobCategory)
                AND (:interviewCategory IS NULL OR i.interviewCategory = :interviewCategory)
                AND (:keyword IS NULL OR i.title LIKE %:keyword% OR qa.questionTitle LIKE %:keyword%)
                AND (i.isDeleted = false)
            ORDER BY
                CASE :sortType
                    WHEN 'RECOMMEND' THEN i.mockCount
                    WHEN 'LATEST' THEN i.createdAt
                    WHEN 'BOOKMARK' THEN i.bookmarkCount
                END DESC,
                CASE :sortType
                    WHEN 'RECOMMEND' THEN i.bookmarkCount
                END DESC,
                i.id ASC
            """)
    Slice<InterviewSetSummaryProjection> findInterviewSetsFirstPage(
            Pageable pageable,
            @Param("jobCategory") JobCategory jobCategory,
            @Param("interviewCategory") InterviewCategory interviewCategory,
            @Param("keyword") String keyword,
            @Param("sortType") String sortType
    );

    @Query("""
            SELECT DISTINCT
                i.id as id,
                i.title as title,
                i.bookmarkCount as bookmarkCount,
                i.mockCount as mockCount,
                i.createdAt as createdAt,
                i.isAnswerPublic as isAnswerPublic,
                m as member
            FROM
                InterviewSet i
                JOIN i.member m
                JOIN i.questionAnswers qa
            WHERE
                (:jobCategory IS NULL OR i.jobCategory = :jobCategory)
                AND (:interviewCategory IS NULL OR i.interviewCategory = :interviewCategory)
                AND (:keyword IS NULL OR i.title LIKE %:keyword% OR qa.questionTitle LIKE %:keyword%)
                AND (i.isDeleted = false)
                AND (
                    (:sortType = 'RECOMMEND' AND ((i.mockCount + i.bookmarkCount) < (:cursorMockCount + :cursorBookmarkCount)
                        OR ((i.mockCount + i.bookmarkCount) = (:cursorMockCount + :cursorBookmarkCount) AND i.id > :cursorId)))
                    OR
                    (:sortType = 'LATEST' AND (i.createdAt < :cursorCreatedAt OR (i.createdAt = :cursorCreatedAt AND i.id > :cursorId)))
                    OR
                    (:sortType = 'BOOKMARK' AND (i.bookmarkCount < :cursorBookmarkCount OR (i.bookmarkCount = :cursorBookmarkCount AND i.id > :cursorId)))
                )
            ORDER BY
                CASE :sortType
                    WHEN 'RECOMMEND' THEN (i.mockCount + i.bookmarkCount)
                    WHEN 'LATEST' THEN i.createdAt
                    WHEN 'BOOKMARK' THEN i.bookmarkCount
                END DESC,
                i.id ASC
            """)
    Slice<InterviewSetSummaryProjection> findInterviewSetsNextPage(
            Pageable pageable,
            @Param("jobCategory") JobCategory jobCategory,
            @Param("interviewCategory") InterviewCategory interviewCategory,
            @Param("keyword") String keyword,
            @Param("sortType") String sortType,
            @Param("cursorId") Long cursorId,
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorBookmarkCount") Integer cursorBookmarkCount,
            @Param("cursorMockCount") Integer cursorMockCount
    );

    @Query("""
            SELECT
                i.id as id,
                i.title as title,
                i.bookmarkCount as bookmarkCount,
                i.mockCount as mockCount,
                i.createdAt as createdAt,
                i.isAnswerPublic as isAnswerPublic,
                m as member
            FROM InterviewSet i
            JOIN i.member m
            WHERE i.member.id = :memberId AND i.isDeleted = false
            ORDER BY i.createdAt DESC, i.id DESC
            """)
    Slice<InterviewSetSummaryProjection> findMyInterviewSetsFirstPage(Pageable pageable, @Param("memberId") Long memberId);

    @Query("""
            SELECT
                i.id as id,
                i.title as title,
                i.bookmarkCount as bookmarkCount,
                i.mockCount as mockCount,
                i.createdAt as createdAt,
                i.isAnswerPublic as isAnswerPublic,
                m as member
            FROM InterviewSet i
            JOIN i.member m
            WHERE i.member.id = :memberId AND i.isDeleted = false AND (i.createdAt < :cursorCreatedAt OR (i.createdAt = :cursorCreatedAt AND i.id < :cursorId))
            ORDER BY i.createdAt DESC, i.id DESC
            """)
    Slice<InterviewSetSummaryProjection> findMyInterviewSetsNextPage(Pageable pageable, @Param("cursorId") Long cursorId, @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt, @Param("memberId") Long memberId);

    @Query("""
            SELECT
                i.id as id,
                i.title as title,
                i.bookmarkCount as bookmarkCount,
                i.mockCount as mockCount,
                i.createdAt as createdAt,
                i.isAnswerPublic as isAnswerPublic,
                m as member,
                b as bookmark
            FROM InterviewSet i
            JOIN i.member m
            JOIN Bookmark b ON i.id = b.interviewSet.id
            WHERE i.isDeleted = false
            AND b.isDeleted = false
            AND b.member.id = :memberId
            ORDER BY b.createdAt DESC, b.id DESC
            """)
    Slice<InterviewSetSummaryProjection> findBookmarkedInterviewSetsFirstPage(Pageable pageable, @Param("memberId") Long memberId);

    @Query("""
            SELECT
                i.id as id,
                i.title as title,
                i.bookmarkCount as bookmarkCount,
                i.mockCount as mockCount,
                i.createdAt as createdAt,
                i.isAnswerPublic as isAnswerPublic,
                m as member,
                b as bookmark
            FROM InterviewSet i
            JOIN i.member m
            JOIN Bookmark b ON i.id = b.interviewSet.id
            WHERE i.isDeleted = false
            AND b.isDeleted = false
            AND b.member.id = :memberId
            AND (b.createdAt < :cursorCreatedAt OR (b.createdAt = :cursorCreatedAt AND b.id < :cursorId))
            ORDER BY b.createdAt DESC, b.id DESC
            """)
    Slice<InterviewSetSummaryProjection> findBookmarkedInterviewSetsNextPage(Pageable pageable, @Param("memberId") Long memberId, @Param("cursorId") Long cursorId, @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt);

    List<InterviewSet> findByMemberId(Long memberId);
}

package com.sw.output.domain.interviewset.repository;

import com.sw.output.domain.interviewset.entity.QuestionAnswer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
    Optional<QuestionAnswer> findByInterviewSetIdAndQuestionTitle(Long interviewSetId, String questionTitle);

    @Query("""
                SELECT qa
                FROM QuestionAnswer qa
                WHERE qa.interviewSet.id = :interviewSetId AND qa.isDeleted = false
                ORDER BY
                CASE WHEN :questionAnswerSortType = 'CREATED_AT' THEN qa.createdAt END ASC,
                CASE WHEN :questionAnswerSortType = 'TITLE' THEN qa.questionTitle END ASC,
                qa.id ASC
            """)
    Slice<QuestionAnswer> findQuestionAnswerFirstPage(
            Pageable pageable,
            @Param("interviewSetId") Long interviewSetId,
            @Param("questionAnswerSortType") String questionAnswerSortType);

    @Query("""
                SELECT qa
                FROM QuestionAnswer qa
                WHERE qa.interviewSet.id = :interviewSetId
                AND qa.isDeleted = false
                AND (
                    (:questionAnswerSortType = 'CREATED_AT' AND (qa.createdAt > :cursorCreatedAt OR (qa.createdAt = :cursorCreatedAt AND qa.id > :cursorId)))
                    OR
                    (:questionAnswerSortType = 'TITLE' AND (qa.questionTitle > :cursorQuestionTitle OR (qa.questionTitle = :cursorQuestionTitle AND qa.id > :cursorId)))
                )
                ORDER BY
                CASE WHEN :questionAnswerSortType = 'CREATED_AT' THEN qa.createdAt END ASC,
                CASE WHEN :questionAnswerSortType = 'TITLE' THEN qa.questionTitle END ASC,
                qa.id ASC
            """)
    Slice<QuestionAnswer> findQuestionAnswerNextPage(
            Pageable pageable,
            @Param("interviewSetId") Long interviewSetId,
            @Param("cursorId") Long cursorId,
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorQuestionTitle") String cursorQuestionTitle,
            @Param("questionAnswerSortType") String questionAnswerSortType);
}

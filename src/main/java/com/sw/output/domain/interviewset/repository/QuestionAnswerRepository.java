package com.sw.output.domain.interviewset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sw.output.domain.interviewset.entity.QuestionAnswer;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
    Optional<QuestionAnswer> findByInterviewSetIdAndQuestionTitle(Long interviewSetId, String questionTitle);
}

package com.sw.output.domain.interviewset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;

public interface InterviewSetRepository extends JpaRepository<InterviewSet, Long> {
    InterviewSetSummaryProjection findSummaryByIdAndIsDeletedFalse(Long id);

    List<InterviewSetSummaryProjection> findByMemberIdAndIsDeletedFalseOrderByCreatedAtDesc(Long memberId);
}

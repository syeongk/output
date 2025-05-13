package com.sw.output.domain.interviewset.repository;

import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewSetRepository extends JpaRepository<InterviewSet, Long> {
    InterviewSetSummaryProjection findSummaryById(Long id);
}

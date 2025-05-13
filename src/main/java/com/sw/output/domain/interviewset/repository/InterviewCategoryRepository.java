package com.sw.output.domain.interviewset.repository;

import com.sw.output.domain.interviewset.entity.InterviewCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewCategoryRepository extends JpaRepository<InterviewCategory, Long> {
    List<InterviewCategory> findAllByNameIn(List<String> categoryNames);
}

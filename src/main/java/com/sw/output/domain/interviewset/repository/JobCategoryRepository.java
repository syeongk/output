package com.sw.output.domain.interviewset.repository;

import com.sw.output.domain.interviewset.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
    List<JobCategory> findAllByNameIn(List<String> categoryNames);
}

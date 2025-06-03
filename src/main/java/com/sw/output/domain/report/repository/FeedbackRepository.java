package com.sw.output.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sw.output.domain.report.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}

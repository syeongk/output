package com.sw.output.domain.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sw.output.domain.complaint.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

}

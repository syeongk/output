package com.sw.output.domain.complaint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sw.output.domain.complaint.entity.Complaint;
import com.sw.output.domain.complaint.entity.ComplaintStatus;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findAllByStatus(ComplaintStatus status);
}

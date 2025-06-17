package com.sw.output.admin.complaint.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sw.output.domain.complaint.entity.Complaint;
import com.sw.output.domain.complaint.entity.ComplaintStatus;
import com.sw.output.domain.complaint.repository.ComplaintRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.ComplaintErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminComplaintService {
    private final ComplaintRepository complaintRepository;

    public List<Complaint> getComplaints(String status) {
        if (status == null) {
            return complaintRepository.findAll();
        }
        return complaintRepository.findAllByStatus(ComplaintStatus.valueOf(status));
    }

    @Transactional
    public void processComplaint(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new BusinessException(ComplaintErrorCode.COMPLIANT_NOT_FOUND));
        complaint.updateStatus(ComplaintStatus.COMPLETED);
    }

    @Transactional
    public void cancelComplaint(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new BusinessException(ComplaintErrorCode.COMPLIANT_NOT_FOUND));
        complaint.updateStatus(ComplaintStatus.PENDING);
    }
}

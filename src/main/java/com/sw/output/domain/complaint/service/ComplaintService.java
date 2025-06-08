package com.sw.output.domain.complaint.service;

import com.sw.output.domain.complaint.converter.ComplaintConverter;
import com.sw.output.domain.complaint.dto.ComplaintRequestDTO;
import com.sw.output.domain.complaint.entity.Complaint;
import com.sw.output.domain.complaint.repository.ComplaintRepository;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import com.sw.output.global.response.errorcode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sw.output.global.util.SecurityUtils.getAuthenticatedUsername;

@Service
@RequiredArgsConstructor
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final InterviewSetRepository interviewSetRepository;
    private final MemberRepository memberRepository;

    public void createComplaint(ComplaintRequestDTO.CreateComplaintDTO request, Long interviewSetId) {
        InterviewSet interviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));

        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Complaint complaint = ComplaintConverter.toComplaint(request, member, interviewSet);
        complaintRepository.save(complaint);
    }
}

package com.sw.output.domain.complaint.converter;

import org.springframework.stereotype.Component;

import com.sw.output.domain.complaint.dto.ComplaintRequestDTO;
import com.sw.output.domain.complaint.entity.Complaint;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.member.entity.Member;

@Component
public class ComplaintConverter {
    public static Complaint toComplaint(ComplaintRequestDTO.CreateComplaintDTO request, Member member, InterviewSet interviewSet) {
        return Complaint.builder()
                .member(member)
                .interviewSet(interviewSet)
                .type(request.getType())
                .content(request.getContent())
                .build();
    }
}

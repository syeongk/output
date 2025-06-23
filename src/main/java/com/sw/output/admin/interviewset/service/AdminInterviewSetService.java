package com.sw.output.admin.interviewset.service;

import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminInterviewSetService {
    private final InterviewSetRepository interviewSetRepository;

    public List<InterviewSet> getInterviewSets() {
        return interviewSetRepository.findAll();
    }

    public List<InterviewSet> getInterviewSetsByMemberId(Long memberId) {
        return interviewSetRepository.findByMemberId(memberId);
    }

    public List<InterviewSet> getInterviewSetsByInterviewSetId(Long interviewSetId) {
        return interviewSetRepository.findById(interviewSetId)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }

    public void hideInterviewSet(Long interviewSetId) {
        InterviewSet interviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));
        interviewSet.hide();
        interviewSetRepository.save(interviewSet);
    }

    public void unhideInterviewSet(Long interviewSetId) {
        InterviewSet interviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));
        interviewSet.unhide();
        interviewSetRepository.save(interviewSet);
    }

    public InterviewSet getInterviewSet(Long interviewSetId) {
        return interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));
    }
}

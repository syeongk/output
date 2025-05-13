package com.sw.output.domain.interviewset.service;

import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.entity.InterviewCategory;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.entity.JobCategory;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import com.sw.output.global.response.errorcode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sw.output.domain.interviewset.converter.InterviewSetConverter.*;

@Service
@RequiredArgsConstructor
public class InterviewSetService {
    private final InterviewSetRepository interviewSetRepository;
    private final InterviewCategoryService interviewCategoryService;
    private final JobCategoryService jobCategoryService;
    private final MemberRepository memberRepository;

    /**
     * 면접 세트를 생성합니다.
     *
     * @param createInterviewSetDTO 면접 세트 생성 요청 DTO
     * @return 생성된 면접 세트의 ID
     * @throws BusinessException 카테고리가 존재하지 않거나, 현재는 하드코딩된 사용자 ID를 사용 중
     */
    @Transactional
    public InterviewSetResponseDTO.CreateInterviewSetDTO createInterviewSet(
            InterviewSetRequestDTO.CreateInterviewSetDTO createInterviewSetDTO
    ) {
        List<JobCategory> jobCategories = jobCategoryService
                .validateAndGetCategories(createInterviewSetDTO.getJobCategories());

        List<InterviewCategory> interviewCategories = interviewCategoryService
                .validateAndGetCategories(createInterviewSetDTO.getInterviewCategories());

        // TODO: 인증 시스템 연동 후 현재 로그인한 사용자 정보로 대체
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        InterviewSet interviewSet = toInterviewSet(member, createInterviewSetDTO);
        interviewSet.setInterviewSetInterviewCategories(interviewCategories);
        interviewSet.setInterviewSetJobCategories(jobCategories);
        interviewSet.setQuestionAnswers(createInterviewSetDTO.getQuestionAnswers());

        interviewSetRepository.save(interviewSet);
        return toCreateInterviewSetResponse(interviewSet.getId());
    }

    /**
     * 면접 세트를 삭제합니다.
     *
     * @param interviewSetId 삭제할 면접 세트 ID
     * @throws BusinessException 면접 세트가 존재하지 않는 경우
     */
    public void deleteInterviewSet(Long interviewSetId) {
        InterviewSet interviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));

        interviewSet.softDelete();

        // TODO : DB 삭제 로직 추가
    }

    public InterviewSetResponseDTO.GetInterviewSetDTO getInterviewSet(Long interviewSetId) {
        InterviewSet interviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));

        if (interviewSet.getIsDeleted()) {
            throw new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND);
        }

        return toGetInterviewSetResponse(interviewSet);
    }
}

package com.sw.output.domain.interviewset.service;

import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.entity.InterviewCategory;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.entity.InterviewSetSortType;
import com.sw.output.domain.interviewset.entity.JobCategory;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.interviewset.repository.BookmarkRepository;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.CommonErrorCode;
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
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;

    /**
     * 면접 세트를 생성합니다.
     *
     * @param createInterviewSetDTO 면접 세트 생성 요청 DTO
     * @return 생성된 면접 세트의 ID
     * @throws BusinessException 카테고리가 존재하지 않거나, 사용자가 존재하지 않는 경우
     */
    @Transactional
    public InterviewSetResponseDTO.InterviewSetIdDTO createInterviewSet(
            InterviewSetRequestDTO.InterviewSetDTO interviewSetDTO) {
        // TODO: 인증 시스템 연동 후 현재 로그인한 사용자 정보로 대체
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        InterviewSet interviewSet = toInterviewSet(member, interviewSetDTO);
        interviewSet.setQuestionAnswers(interviewSetDTO.getQuestionAnswers());

        interviewSetRepository.save(interviewSet);
        return toInterviewSetIdResponse(interviewSet.getId());
    }

    /**
     * 면접 세트를 삭제합니다.
     *
     * @param interviewSetId 삭제할 면접 세트 ID
     * @throws BusinessException 면접 세트가 존재하지 않는 경우
     */
    @Transactional
    public void deleteInterviewSet(Long interviewSetId) {
        InterviewSet interviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));

        // TODO: 인증 시스템 연동 후 현재 로그인한 사용자 정보로 대체
        if (!interviewSet.getMember().getId().equals(1L)) {
            throw new BusinessException(CommonErrorCode.FORBIDDEN);
        }

        bookmarkRepository.deleteByInterviewSetId(interviewSetId);

        // TODO : DB 삭제 로직 추가
        interviewSet.softDelete();
    }

    /**
     * 면접 세트를 조회합니다.
     *
     * @param interviewSetId 조회할 면접 세트 ID
     * @return 조회된 면접 세트
     * @throws BusinessException 면접 세트가 존재하지 않는 경우, 삭제된 면접 세트인 경우, 사용자가 존재하지 않는 경우
     */
    public InterviewSetResponseDTO.GetInterviewSetDTO getInterviewSet(Long interviewSetId) {
        InterviewSet interviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));

        if (interviewSet.getIsDeleted()) {
            throw new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND);
        }

        return toGetInterviewSetResponse(interviewSet);
    }

    /**
     * 면접 세트를 복제합니다.
     *
     * @param interviewSetId  복제할 면접 세트 ID
     * @param interviewSetDTO 복제할 면접 세트 생성 요청 DTO
     * @return 생성된 면접 세트의 ID
     * @throws BusinessException 면접 세트가 존재하지 않는 경우, 카테고리가 존재하지 않는 경우, 사용자가 존재하지 않는
     *                           경우
     */
    @Transactional
    public InterviewSetResponseDTO.InterviewSetIdDTO duplicateInterviewSet(Long interviewSetId,
                                                                           InterviewSetRequestDTO.InterviewSetDTO interviewSetDTO) {
        // TODO: 인증 시스템 연동 후 현재 로그인한 사용자 정보로 대체
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        InterviewSet parentInterviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));

        if (parentInterviewSet.getIsDeleted()) {
            throw new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_DELETED);
        }

        InterviewSet interviewSet = toInterviewSet(member, interviewSetDTO, parentInterviewSet);
        interviewSet.setQuestionAnswers(interviewSetDTO.getQuestionAnswers());

        interviewSetRepository.save(interviewSet);
        return toInterviewSetIdResponse(interviewSet.getId());
    }

    /**
     * 면접 세트를 수정합니다.
     *
     * @param interviewSetId  수정할 면접 세트 ID
     * @param interviewSetDTO 수정할 면접 세트 생성 요청 DTO
     * @throws BusinessException 사용자 정보가 일치하지 않는 경우, 삭제된 면접 세트인 경우, 면접 세트 ID가 존재하지
     *                           않는 경우, 카테고리가 존재하지 않는 경우
     */
    @Transactional
    public InterviewSetResponseDTO.InterviewSetIdDTO updateInterviewSet(Long interviewSetId,
                                                                        InterviewSetRequestDTO.InterviewSetDTO interviewSetDTO) {
        InterviewSet interviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));

        if (interviewSet.getIsDeleted()) {
            throw new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_DELETED);
        }

        // TODO: 인증 시스템 연동 후 현재 로그인한 사용자 정보로 대체
        if (!interviewSet.getMember().getId().equals(1L)) {
            throw new BusinessException(CommonErrorCode.FORBIDDEN);
        }

        interviewSet.setQuestionAnswers(interviewSetDTO.getQuestionAnswers());
        interviewSet.setIsAnswerPublic(interviewSetDTO.getIsAnswerPublic());
        interviewSet.setTitle(interviewSetDTO.getTitle());

        return toInterviewSetIdResponse(interviewSetId);
    }

    public List<InterviewSetSummaryProjection> getInterviewSets(JobCategory jobCategory, InterviewCategory interviewCategory, String keyword,
                                                                InterviewSetSortType sortType, int size, int cursor) {
        if (sortType == null) {
            sortType = InterviewSetSortType.RECOMMEND;
        }

        List<InterviewSetSummaryProjection> interviewSets = interviewSetRepository.findInterviewSets(jobCategory, interviewCategory, keyword, sortType.name(), size, cursor);

        return interviewSets;
    }
}

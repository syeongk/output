package com.sw.output.domain.interviewset.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.output.domain.interviewset.dto.InterviewSetRequestDTO;
import com.sw.output.domain.interviewset.dto.InterviewSetResponseDTO;
import com.sw.output.domain.interviewset.dto.OpenAIResponseDTO;
import com.sw.output.domain.interviewset.entity.InterviewCategory;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.entity.InterviewSetSortType;
import com.sw.output.domain.interviewset.entity.JobCategory;
import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.interviewset.repository.BookmarkRepository;
import com.sw.output.domain.interviewset.repository.InterviewSetRepository;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.domain.report.entity.Report;
import com.sw.output.domain.report.repository.ReportRepository;
import com.sw.output.global.converter.CommonConverter;
import com.sw.output.global.dto.CommonResponseDTO;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.CommonErrorCode;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import com.sw.output.global.response.errorcode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sw.output.domain.interviewset.converter.InterviewSetConverter.*;
import static com.sw.output.domain.report.converter.ReportConverter.toReport;
import static com.sw.output.global.util.SecurityUtils.getAuthenticatedUsername;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewSetService {
    private final InterviewSetRepository interviewSetRepository;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate openAITemplate;
    private final ReportRepository reportRepository;

    @Value("${openai.api.model}")
    private String model;

    /**
     * 면접 세트를 생성합니다.
     *
     * @param interviewSetDTO 면접 세트 생성 요청 DTO
     * @return 생성된 면접 세트의 ID
     * @throws BusinessException 카테고리가 존재하지 않거나, 사용자가 존재하지 않는 경우
     */
    @Transactional
    public InterviewSetResponseDTO.InterviewSetIdDTO createInterviewSet(
            InterviewSetRequestDTO.InterviewSetDTO interviewSetDTO) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
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

        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!interviewSet.getMember().getId().equals(member.getId())) {
            throw new BusinessException(CommonErrorCode.FORBIDDEN);
        }

        bookmarkRepository.deleteByInterviewSetId(interviewSetId);

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
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
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

        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!interviewSet.getMember().getId().equals(member.getId())) {
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

    public InterviewSetResponseDTO.GetQuestionsDTO createAiQuestions(InterviewSetRequestDTO.CreateQuestionsPromptDTO createQuestionsPromptDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();

        String prompt = """
                당신은 %s 직무의 %s 면접을 진행하는 면접관입니다.

                [입력 정보]
                직무 : %s
                면접 유형 : %s
                주제 : %s

                [출력 형식]
                총 %s개의 면접 질문을 JSON 배열 형태로 생성해 주세요.
                응답 예시 : ["질문1", "질문2", "질문3", ...]
                """.formatted(
                createQuestionsPromptDTO.getJobCategory(),
                createQuestionsPromptDTO.getInterviewCategory(),
                createQuestionsPromptDTO.getJobCategory(),
                createQuestionsPromptDTO.getInterviewCategory(),
                createQuestionsPromptDTO.getTitle(),
                createQuestionsPromptDTO.getQuestionCount());

        body.put("model", model);
        body.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        OpenAIResponseDTO.AIQuestionsDTO response = openAITemplate.postForObject(
                "https://api.openai.com/v1/chat/completions",
                request,
                OpenAIResponseDTO.AIQuestionsDTO.class
        );

        log.info(response.getChoices().get(0).getMessage().getContent());

        String questions = response.getChoices().get(0).getMessage().getContent();
        try {
            List<String> list = objectMapper.readValue(questions, new TypeReference<List<String>>() {
            });
            return toGetQuestionsDTO(list);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public CommonResponseDTO.IdResponseDTO startMockInterview(Long interviewSetId) {
        InterviewSet interviewSet = interviewSetRepository.findById(interviewSetId)
                .orElseThrow(() -> new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_NOT_FOUND));

        if (interviewSet.getIsDeleted()) {
            throw new BusinessException(InterviewSetErrorCode.INTERVIEW_SET_DELETED);
        }

        interviewSet.increaseMockCount();

        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        Report report = toReport(interviewSet, member);
        reportRepository.save(report);

        return CommonConverter.toIdResponseDTO(report.getId());
    }
}

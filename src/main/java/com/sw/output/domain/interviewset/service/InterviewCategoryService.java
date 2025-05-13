package com.sw.output.domain.interviewset.service;

import com.sw.output.domain.interviewset.entity.InterviewCategory;
import com.sw.output.domain.interviewset.repository.InterviewCategoryRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewCategoryService {
    private final InterviewCategoryRepository interviewCategoryRepository;

    /**
     * 면접 카테고리 이름이 존재하는지 확인하고, 존재하는 경우 엔티티 리스트로 반환합니다.
     *
     * @param categoryNames 면접 카테고리 이름 리스트
     * @return 면접 카테고리 엔티티 리스트
     * @throws BusinessException 면접 카테고리 이름이 존재하지 않음
     */
    public List<InterviewCategory> validateAndGetCategories(List<String> categoryNames) {
        List<InterviewCategory> categories = interviewCategoryRepository.findAllByNameIn(categoryNames);

        if (categories.size() != categoryNames.size()) {
            throw new BusinessException(InterviewSetErrorCode.INTERVIEW_CATEGORY_NOT_FOUND);
        }

        return categories;
    }
} 

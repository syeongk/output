package com.sw.output.domain.interviewset.service;

import com.sw.output.domain.interviewset.entity.JobCategory;
import com.sw.output.domain.interviewset.repository.JobCategoryRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.InterviewSetErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCategoryService {
    private final JobCategoryRepository jobCategoryRepository;

    /**
     * 직무 카테고리 이름이 존재하는지 확인하고, 존재하는 경우 엔티티 리스트로 반환합니다.
     *
     * @param categoryNames 직무 카테고리 이름 리스트
     * @return 직무 카테고리 엔티티 리스트
     * @throws BusinessException 직무 카테고리 이름이 존재하지 않음
     */
    public List<JobCategory> validateAndGetCategories(List<String> categoryNames) {
        List<JobCategory> categories = jobCategoryRepository.findAllByNameIn(categoryNames);

        if (categories.size() != categoryNames.size()) {
            throw new BusinessException(InterviewSetErrorCode.JOB_CATEGORY_NOT_FOUND);
        }

        return categories;
    }
}

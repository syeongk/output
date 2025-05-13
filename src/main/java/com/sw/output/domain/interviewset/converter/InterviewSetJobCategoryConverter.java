package com.sw.output.domain.interviewset.converter;

import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.entity.InterviewSetJobCategory;
import com.sw.output.domain.interviewset.entity.JobCategory;

public class InterviewSetJobCategoryConverter {
    public static InterviewSetJobCategory toInterviewSetJobCategory(InterviewSet interviewSet, JobCategory jobCategory) {
        return InterviewSetJobCategory.builder()
                .interviewSet(interviewSet)
                .jobCategory(jobCategory)
                .build();
    }
}

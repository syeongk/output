package com.sw.output.domain.interviewset.converter;

import com.sw.output.domain.interviewset.entity.InterviewCategory;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewset.entity.InterviewSetInterviewCategory;

public class InterviewSetInterviewCategoryConverter {
    public static InterviewSetInterviewCategory toInterviewSetInterviewCategory(InterviewSet interviewSet, InterviewCategory interviewCategory) {
        return InterviewSetInterviewCategory.builder()
                .interviewSet(interviewSet)
                .interviewCategory(interviewCategory)
                .build();
    }
}

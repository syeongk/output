package com.sw.output.domain.interviewset.converter;

import com.sw.output.domain.interviewset.entity.InterviewCategory;
import com.sw.output.domain.interviewset.entity.InterviewSet;

public class InterviewSetInterviewCategoryConverter {
    public static com.sw.output.domain.mapping.entity.InterviewSetInterviewCategory toInterviewSetInterviewCategory(InterviewSet interviewSet, InterviewCategory interviewCategory) {
        return com.sw.output.domain.mapping.entity.InterviewSetInterviewCategory.builder()
                .interviewSet(interviewSet)
                .interviewCategory(interviewCategory)
                .build();
    }
}

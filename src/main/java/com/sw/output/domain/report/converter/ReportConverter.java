package com.sw.output.domain.report.converter;

import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.report.entity.Report;

public class ReportConverter {
    public static Report toReport(InterviewSet interviewSet, Member member) {
        return Report.builder()
                .interviewSet(interviewSet)
                .member(member)
                .build();
    }
}
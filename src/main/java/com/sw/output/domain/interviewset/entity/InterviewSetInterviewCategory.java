package com.sw.output.domain.mapping.entity;

import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.interviewcategory.entity.InterviewCategory;
import com.sw.output.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InterviewSetInterviewCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 면접세트 면접 카테고리 ID

    // 면접세트와 N:1 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_set_id", nullable = false)
    private InterviewSet interviewSet;

    // 면접 카테고리와 N:1 연관관계 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_category_id", nullable = false)
    private InterviewCategory interviewCategory;
}

package com.sw.output.domain.jobcategory.entity;

import com.sw.output.domain.BaseEntity;
import com.sw.output.domain.interviewset.entity.InterviewSetJobCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class JobCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 직무 카테고리 ID

    @Column(nullable = false, length = 30)
    private String name; // 직무 카테고리 이름

    // 면접 세트 직무 카테고리와 1:N 연관관계
    @OneToMany(mappedBy = "jobCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewSetJobCategory> interviewSetJobCategories = new ArrayList<>();
}

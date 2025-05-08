package com.sw.output.domain.interviewcategory.entity;

import com.sw.output.domain.BaseEntity;
import com.sw.output.domain.mapping.entity.InterviewSetInterviewCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InterviewCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 면접 카테고리 ID

    @Column(nullable = false, length = 20)
    private String name; // 면접 카테고리 이름

    // 면접 세트 면접 카테고리와 1:N 연관관계
    @OneToMany(mappedBy = "interviewCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewSetInterviewCategory> interviewSetInterviewCategories = new ArrayList<>();
}

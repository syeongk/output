package com.sw.output.domain.notice.entity;

import com.sw.output.domain.common.SoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notice extends SoftDeleteEntity {
    @Column(nullable = false)
    private String title; // 공지사항 제목

    @Column(nullable = false)
    private String content; // 공지사항 내용
}

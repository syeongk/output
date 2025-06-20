package com.sw.output.domain.admin;

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
public class Admin extends SoftDeleteEntity {
    @Column(nullable = false, length = 50, unique = true)
    private String username; // 아이디

    @Column(nullable = false, length = 100)
    private String password; // BCrypt 암호화 비밀번호
}
package com.sw.output.domain.common;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class SoftDeleteEntity extends BaseEntity {
    @Column
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void restore() {
        this.isDeleted = false;
        this.deletedAt = null;
    }
}

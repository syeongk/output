package com.sw.output.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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
}

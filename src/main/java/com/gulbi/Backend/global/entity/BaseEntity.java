package com.gulbi.Backend.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass  // 공통 엔티티로 사용
@EntityListeners(AuditingEntityListener.class)  // JPA Auditing 활성화
public abstract class BaseEntity {

    private LocalDateTime deletedAt;  // 소프트 삭제를 위한 필드

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist  // 엔티티가 처음 저장될 때 자동 호출
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate  // 엔티티가 업데이트될 때 자동 호출
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 소프트 삭제 처리: 삭제 시점을 설정하고, 실제 삭제는 하지 않음
    public void markAsDeleted() {
        if (this.deletedAt == null) {
            this.deletedAt = LocalDateTime.now();
        }
    }

    // 삭제 여부 확인
    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    // 특정 시점에서 생성된 엔티티인지 여부를 확인하는 메서드 (불변성 유지)
    public boolean isCreatedAfter(LocalDateTime time) {
        return createdAt.isAfter(time);
    }

    // 업데이트된 시간을 명시적으로 설정하는 메서드는 만들지 않고, 자동으로 업데이트됨
}

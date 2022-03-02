package com.adiscope.kkpoint.config.token;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by 최경근(keun0912@neowiz.com) on 2020-02-23.
 */
@MappedSuperclass
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class BaseEntity implements Serializable {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

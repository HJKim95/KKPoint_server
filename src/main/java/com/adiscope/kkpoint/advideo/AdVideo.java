package com.adiscope.kkpoint.advideo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "kk_ad_video")
public class AdVideo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private String vid;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column
    private Boolean displayFlag;
    @CreatedDate
    private LocalDateTime createdAt;
}
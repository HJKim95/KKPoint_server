package com.adiscope.kkpoint.user_withdraw;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "kk_user_withdraw")
public class UserWithdraw {

    private static final long serialVersionUID = 1L;

    @Id
    private Long uid;
    @Column
    private String email;
    @Column
    private String socialType;
    @CreatedDate
    private LocalDateTime createdAt;
}
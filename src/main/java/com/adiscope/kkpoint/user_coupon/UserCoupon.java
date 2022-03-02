package com.adiscope.kkpoint.user_coupon;

import com.adiscope.kkpoint.user.User;
import lombok.*;
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
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "kk_user_coupon")
public class UserCoupon {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
    @Column
    private String couponId;
    @CreatedDate
    private LocalDateTime createdAt;
    @Column
    private LocalDate validDate;
}
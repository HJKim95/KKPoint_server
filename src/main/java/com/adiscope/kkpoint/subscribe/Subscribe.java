package com.adiscope.kkpoint.subscribe;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder // 알아서 빌더 추가
@DynamicInsert // JPA로 Insert시 빈필드는 기본값 적용
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Table(name = "kk_subscribe")
public class Subscribe {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SubscribePK uidAndCid;
    @CreatedDate
    private LocalDateTime createdAt;
}

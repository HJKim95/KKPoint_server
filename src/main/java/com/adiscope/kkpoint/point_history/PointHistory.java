package com.adiscope.kkpoint.point_history;

import com.adiscope.kkpoint.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kk_point_history")
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
//    private Long uid;

    private Integer amount;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

}

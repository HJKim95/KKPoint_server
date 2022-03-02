package com.adiscope.kkpoint.attendance;

import com.adiscope.kkpoint.user.User;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kk_attendance")
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
//    private Long uid;

    @CreatedDate
    private LocalDate createdAt;

}

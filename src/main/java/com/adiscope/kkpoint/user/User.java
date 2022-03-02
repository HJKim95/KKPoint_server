package com.adiscope.kkpoint.user;

import com.adiscope.kkpoint.attendance.Attendance;
import com.adiscope.kkpoint.point_history.PointHistory;
import com.adiscope.kkpoint.user_coupon.UserCoupon;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kk_user")
@ToString(exclude = {"attendanceList", "pointHistoryList"})
@EntityListeners(AuditingEntityListener.class) // 얘랑 밑에있는 @CreateData 두개가 객체에서부터 바로 날짜 넣어주는 역할
@Builder
@Accessors(chain = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String socialType;

    private String name;

    private String email;
    
    private Boolean enableAlarm;

    private LocalDate enableAlarmDate;

    @CreatedDate
    private Timestamp createdAt;

    @LastModifiedDate
    private Timestamp updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Attendance> attendanceList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<PointHistory> pointHistoryList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserCoupon> userCouponList;
}

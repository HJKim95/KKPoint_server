package com.adiscope.kkpoint.attendance;

import com.adiscope.kkpoint.point_history.PointHistory;
import com.adiscope.kkpoint.user.User;
import com.adiscope.kkpoint.video.Video;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserAndCreatedAt(User user, LocalDate date);

    @Query("SELECT attend FROM Attendance attend WHERE attend.user.idx = :id ")
    List<AttendanceApiResponse> findByUserId(Long id);
}

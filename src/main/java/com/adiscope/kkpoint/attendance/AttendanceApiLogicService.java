package com.adiscope.kkpoint.attendance;

import com.adiscope.kkpoint.common.custom_exception.UserRedundantAttendException;
import com.adiscope.kkpoint.point_history.PointApiLogicService;
import com.adiscope.kkpoint.point_history.PointApiRequest;
import com.adiscope.kkpoint.user.User;
import com.adiscope.kkpoint.user.UserRepository;
import com.adiscope.kkpoint.common.Header;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceApiLogicService {

    private final UserRepository userRepository;

    private final AttendanceRepository attendanceRepository;

    private final PointApiLogicService pointApiLogicService;

    @Transactional
    public Header<AttendanceApiResponse> createAttend(Long id) throws IllegalAccessException {

        // JWT로 id 가져오고,
        // 해당 일에 출석체크를 했는지 먼저 체크후 (앱에서 체크하겠지만 한번더 체크)
        // 출석체크 해주고(attendance db에 데이터 추가)
        // 포인트 추가해주기(point_history db에 데이터 추가)

        // user
        User user = userRepository.getOne(id);

        LocalDate today = LocalDate.now();

        // 출석체크가 이미 되어있다면, return error
        Optional<Attendance> checkAttendance = attendanceRepository.findByUserAndCreatedAt(user, today);
        if (checkAttendance.isPresent()) {
            throw new UserRedundantAttendException();
//            return Header.ERROR("데이터 이미 존재");
        }

        // 출석체크 해주기
        Attendance attendance = Attendance.builder()
                .user(user)
                .build();

        PointApiRequest pointApiRequest = PointApiRequest.builder().amount(1000).content("출석체크").build();
        pointApiLogicService.create(pointApiRequest, id);

        return response(attendanceRepository.save(attendance));
    }

    public Header<AttendanceApiResponse> response(Attendance attendance){
        // user -> userApiResponse

        AttendanceApiResponse attendanceApiResponse = AttendanceApiResponse.builder()
                .idx(attendance.getIdx())
                .uid(attendance.getUser().getIdx())
                .createdAt(attendance.getCreatedAt())
                .build();

        // Header + data return
        return Header.OK(attendanceApiResponse);
    }
}

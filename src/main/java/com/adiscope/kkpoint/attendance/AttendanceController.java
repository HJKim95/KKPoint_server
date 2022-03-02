package com.adiscope.kkpoint.attendance;

import com.adiscope.kkpoint.common.CRUDController;
import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.user.UserApiLogicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Api(tags = {"Attendance"})
@RestController
@RequestMapping("/kkpoint/attendance")
public class AttendanceController {

    protected String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userId;
    }

    @Autowired
    AttendanceApiLogicService attendanceApiLogicService;

    @ApiOperation(value = "유저의 출석체크 등록", notes = "유저가 출석체크를 한다. 출석체크 시 자동으로 1000포인트도 추가가 되고(pointHistory) 서버시간을 기준으로 출석체크 등록이 된다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("")
    public Header<AttendanceApiResponse> createAttendance() throws IllegalAccessException {
        String userId = getUserId();
        Long id = Long.parseLong(userId);
        log.info("{}",id);
        return attendanceApiLogicService.createAttend(id);
    }

}

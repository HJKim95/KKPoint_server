package com.adiscope.kkpoint.user;

import com.adiscope.kkpoint.attendance.AttendanceApiResponse;
import com.adiscope.kkpoint.common.CRUDController;
import com.adiscope.kkpoint.common.Header;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Slf4j
@Api(tags = {"User"})
@RestController
@RequestMapping("/kkpoint/user")
public class UserController {

    protected String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userId;
    }

    @Autowired
    UserApiLogicService userApiLogicService;

    @ApiOperation(value = "회원정보 가져오기", notes = "회원정보를 가져온다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("")
    public Header<UserApiResponse> getMyInfo() {
        String userId = getUserId();
        Long id = Long.parseLong(userId);
        return userApiLogicService.read(id);
    }

    @ApiOperation(value = "회원정보 수정", notes = "회원정보를 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("")
    public Header<UserApiResponse> updateMyInfo(@RequestBody UserApiRequest request) {
        String userId = getUserId();
        Long id = Long.parseLong(userId);
        return userApiLogicService.update(request, id);
    }

    @ApiOperation(value = "회원탈퇴", notes = "회원탈퇴를 한다. 회원탈퇴 시 자동적으로 출석, 포인트 내역도 DB에서 삭제되 withDraw 테이블에 등록된다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping("")
    public Header<UserApiResponse> deleteMyInfo() {
        String userId = getUserId();
        Long id = Long.parseLong(userId);
        return userApiLogicService.delete(id);
    }

    @ApiOperation(value = "출석체크 내역", notes = "출석체크 내역을 확인한다. date: 확인하고자 하는 년월을 입력한다(ex. 20210201) 단, 일의 경우 1일로 명시한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/attendance")
    public Header<List<AttendanceApiResponse>> readMonthAttendInfo(@RequestParam(value = "date") String  date) {
        String userId = getUserId();
        Long id = Long.parseLong(userId);
        return userApiLogicService.attendInfo(id, date);
    }


    @ApiOperation(value = "포인트충전/소비내역", notes = "내정보-포인트충전/소비내역을 확인한다. paging = 20")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/pointHistory")
    public Header<UserPointInfoApiResponse> pointInfo(@RequestParam(value = "page") Integer page) {
        String userId = getUserId();
        Long id = Long.parseLong(userId);
        return userApiLogicService.pointInfo(id,page,20);
    }
}

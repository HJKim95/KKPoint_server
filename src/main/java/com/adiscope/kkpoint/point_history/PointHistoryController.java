package com.adiscope.kkpoint.point_history;

import com.adiscope.kkpoint.attendance.AttendanceApiLogicService;
import com.adiscope.kkpoint.common.CRUDController;
import com.adiscope.kkpoint.common.Header;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = {"PointHistory"})
@RestController
@RequestMapping("/kkpoint/pointHistory")
public class PointHistoryController {

    protected String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userId;
    }

    @Autowired
    PointApiLogicService pointApiLogicService;

    @ApiOperation(value = "포인트 사용 소진 등록", notes = "유저가 포인트를 사용하거나 획득할때 사용한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("")
    public Header<PointApiResponse> create(@RequestBody PointApiRequest request) {
        String userId = getUserId();
        Long id = Long.parseLong(userId);
        log.info("{}",request);
        return pointApiLogicService.create(request, id);
    }
}

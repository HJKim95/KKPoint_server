package com.adiscope.kkpoint.subscribe;

import com.adiscope.kkpoint.common.Header;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = {"Subscribe"})
@RestController
@RequestMapping(value = "/kkpoint")
public class SubscribeController {

    // 토큰에서 uid 가져오기
    protected String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userId;
    }
    // pk 차이 때문에 별도로 처리
    @Autowired
    protected SubscribeService subscribeService;

    // 구독하기
    @ApiOperation(value = "채널 구독", notes = "채널을 구독한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/subscribe")    // /test/user
    public Header<SubscribeApiResponse> create(@RequestParam String cid) {
        String userId = getUserId();
        Long uid = Long.parseLong(userId);
        return subscribeService.create(uid, cid);
    }

    // 구독 해제하기
    @ApiOperation(value = "채널 구독 해지", notes = "채널 구독을 해지한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping("/subscribe")  // /test/user/
    public Header delete(@RequestParam String cid) {
        String userId = getUserId();
        Long uid = Long.parseLong(userId);
        return subscribeService.delete(uid, cid);
    }

    // 구독 상태
    @ApiOperation(value = "채널 구독 상태 확인", notes = "해당 유저가 해당 채널을 구독하고 있는지 확인한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/subscribe/status")
    public Header<Boolean> getSubscribeStatus(@RequestParam String cid) {
        String userId = getUserId();
        Long uid = Long.parseLong(userId);
        return subscribeService.getSubscribeStatus(uid, cid);
    }
}

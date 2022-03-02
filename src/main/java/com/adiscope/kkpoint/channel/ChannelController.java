package com.adiscope.kkpoint.channel;

import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.common.Pagination;
import com.adiscope.kkpoint.subscribe.SubscribeService;
import com.adiscope.kkpoint.video.VideoApiResponse;
import com.adiscope.kkpoint.video.VideoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"Channel"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/kkpoint")
public class ChannelController {

    // 토큰에서 uid 뽑아오기
    protected String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userId;
    }

    @Autowired
    private ChannelService channelService;
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private VideoService videoService;

    // 내가 구독한 채널들 받아오기
    @ApiOperation(value = "유저의 구독 채널 목록", notes = "유저가 구독한 채널목록을 가져온다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/channels/subscribed")
    public Header<List<ChannelApiResponse>> readSubscribedChannels() {
        String userId = getUserId();
        Long uid = Long.parseLong(userId);
        return channelService.readMySubscribeChannels(uid);
    }

//    // 채널 검색
//    @GetMapping("/channels/search")
//    public Header<List<ChannelApiResponse>> readSearchChannels(@RequestParam(value = "searchText") String searchText) {
//        return channelService.readSearchChannels(searchText);
//    }

    // 채널 홈
    @ApiOperation(value = "채널 영상 확인", notes = "해당 채널의 영상을 가져오고 채널 구독여부를 가져온다. (검색화면, 구독화면 에서 채널을 눌렀을때)\nhttps://app.zeplin.io/project/60112dd7c68c9729513e8386/screen/603f2182aad299acbd5f3b82")
    @GetMapping("/channel/home")
    public Header<ChannelHomeResponse> readSearchChannels(@RequestParam(value = "uid") Long uid,
                                                          @RequestParam(value = "cid") String cid) {
        ChannelApiResponse channelApiResponse = channelService.read(cid).getData();
        Boolean isSubscribed = subscribeService.getSubscribeStatus(uid, cid).getData();
        Pageable p = PageRequest.of(0, 20);

        Header<List<VideoApiResponse>> buf = videoService.readChannelVideosPaging(cid, p);
        List<VideoApiResponse> videoList = buf.getData();

        ChannelHomeResponse response = ChannelHomeResponse.builder()
                .channel(channelApiResponse)
                .isSubscribed(isSubscribed)
                .videoList(videoList)
                .build();

        return Header.OK(response, buf.getPagination());
    }
}

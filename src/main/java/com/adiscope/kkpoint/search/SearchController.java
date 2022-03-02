package com.adiscope.kkpoint.search;

import com.adiscope.kkpoint.channel.ChannelApiResponse;
import com.adiscope.kkpoint.channel.ChannelService;
import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.subscribe.SubscribeService;
import com.adiscope.kkpoint.video.VideoApiResponse;
import com.adiscope.kkpoint.video.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = {"Search"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/kkpoint")
public class SearchController {

    @Autowired
    protected VideoService videoService;
    @Autowired
    protected ChannelService channelService;
    @Autowired
    protected SubscribeService subscribeService;

    // 통합 검색
    @ApiOperation(value = "통합 검색", notes = "통합 검색을 한다.(채널, 영상 구독 여부)")
    @GetMapping("/search")
    public Header<SearchResponse> readSearchAll(@RequestParam(value = "uid") Long uid,
                                                @RequestParam(value = "searchText") String searchText) {
        // 비디오 검색 결과
        List<VideoApiResponse> videoApiResponseList = videoService.readSearchVideos(searchText).getData();
        // 채널 검색 결과
        List<ChannelApiResponse> channelApiResponseList = channelService.readSearchChannels(searchText).getData();
        // 구독 여부
        List<Boolean> subscribeList = channelApiResponseList.stream()
                .map(entity -> {
                    return subscribeService.getSubscribeStatus(uid, entity.getCid()).getData();
                }).collect(Collectors.toList());

        // 리스폰 만들고 리턴
        return Header.OK(
                SearchResponse.builder()
                    .videoList(videoApiResponseList)
                    .channelList(channelApiResponseList)
                    .subscribeList(subscribeList)
                    .build());
    }

    // 검색중 구독 관련 refresh
    @ApiOperation(value = "검색중 구독 관련 refresh", notes = "검색화면 도중 refresh 하는 경우 채널에 대한 구독정보만 빠르게 다시 확인한다.")
    @GetMapping("/search/refreshChannel")
    public Header<List<Boolean>> readSearchRefreshChannel(@RequestParam(value = "uid") Long uid,
                                                @RequestParam(value = "searchText") String searchText) {
        // 채널 검색 결과
        List<ChannelApiResponse> channelApiResponseList = channelService.readSearchChannels(searchText).getData();
        // 구독 여부
        List<Boolean> subscribeList = channelApiResponseList.stream()
                .map(entity -> {
                    return subscribeService.getSubscribeStatus(uid, entity.getCid()).getData();
                }).collect(Collectors.toList());

        // 리스폰 만들고 리턴
        return Header.OK(subscribeList);
    }
}

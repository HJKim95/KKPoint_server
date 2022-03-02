package com.adiscope.kkpoint.video;

import com.adiscope.kkpoint.common.Header;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"Video"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/kkpoint")
public class VideoController {

    // 토큰에서 uid 뽑아오기
    protected String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userId;
    }

    // pk 차이 때문에 별도로 처리
    @Autowired
    protected VideoService videoService;

    @Autowired
    protected VideoItemService videoItemService;


    // 모든 비디오 받아오기
    @ApiOperation(value = "모든 비디오 가져오기", notes = "모든 비디오 영상을 가져온다. (메인홈탭, 인기탭)")
    @GetMapping("/videos")
    public Header<List<VideoApiResponse>> readAllPaging(@RequestParam(value = "page") Integer page) {
        Pageable p = PageRequest.of(page, 20);
        return videoService.readAllPaging(p);
    }

    @ApiOperation(value = "영상에 조회수 추가", notes = "해당 영상에 조회수 1을 추가한다.")
    @GetMapping("/videos/update")
    public Header<VideoApiResponse> updateVideoViews(@RequestParam(value = "vid") String vid) {
        return videoService.updateViews(vid);
    }

    // 한 채널의 비디오들 받아오기
    @ApiOperation(value = "해당 채널의 모든 비디오 가져오기", notes = "해당 채널의 모든 비디오를 가져온다. paging = 20")
    @GetMapping("/videos/channel/{cid}")
    public Header<List<VideoApiResponse>> readChannelVideosPaging(@PathVariable String cid, @RequestParam(value = "page") Integer page) {
        Pageable p = PageRequest.of(page, 20);
        return videoService.readChannelVideosPaging(cid, p);
    }

//    // 홍보영상 (자신제외, 랜덤, 3개), 채널영상 (자신제외, 랜덤, 5개), 전체영상 (자신제외, 랜덤, 50개) 뽑고 채홍채홍채채채전전전전전..50개 리스트로 만든 뒤 중복제거해서 클라이언트에 넣어주기
//    @GetMapping("/videos/related/{vid}")
//    public Header<VideoRelatedApiResponse> readRelatedVideos(@PathVariable String vid) {
//        return videoService.readRelatedVideos(vid);
//    }

    // 내가 구독한 채널들의 영상들 받기
    @ApiOperation(value = "해당 유저의 구독한 채널의 모든 영상 가져오기", notes = "해당 유저가 구독한 모든 채널의 모든 영상을 가져온다. ( 구독탭 ) paging = 20")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/videos/subscribed")
    public Header<List<VideoApiResponse>> readSubscribedVideosPaging(@RequestParam(value = "page") Integer page) {
        String userId = getUserId();
        Long uid = Long.parseLong(userId);
        Pageable p = PageRequest.of(page, 20);
        return videoService.readMySubscribeVideosPaging(uid, p);
    }

//    // 비디오 검색
//    @GetMapping("/videos/search")
//    public Header<List<VideoApiResponse>> readSearchVideos(@RequestParam(value = "searchText") String searchText) {
//        return videoService.readSearchVideos(searchText);
//    }

    // 연관상품
    @ApiOperation(value = "해당 영상의 연관된 상품 모두 가져오기", notes = "해당 영상의 연관된 상품 모두 가져온다.")
    @GetMapping("/video/relatedItem/{vid}")
    Header<List<VideoItemDTO>> readAllVideoItems(@PathVariable String vid) {
        return videoItemService.readAllVideoItems(vid);
    }
}

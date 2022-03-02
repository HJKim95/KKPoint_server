package com.adiscope.kkpoint.video;

import com.adiscope.kkpoint.advideo.AdVideoRepository;
import com.adiscope.kkpoint.channel.ChannelRepository;
import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.common.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by 박영호(eeyatho@neowiz.com) on 2021-01-26.
 */
@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final ChannelRepository channelRepository;
    private final AdVideoRepository adVideoRepository;

    // 영상 추가하기
    @Transactional
    public Header<VideoApiResponse> create(VideoApiRequest request) {
        // 1. 이미 있으면 업데이트 안하기
        if (videoRepository.existsById(request.getVid()))
            return Header.ERROR("vid Error : 이미 등록되어 있습니다.");

        // 2. cid 유효성 체크
//        if (!channelRepository.existsById(request.getCid()))
//            return Header.ERROR("cid Error : 등록되지 않은 채널입니다.");

        // 3. request로 엔티티 만들
        Video video = Video.builder()
                .vid(request.getVid())
                .channel(channelRepository.findByCid(request.getCid()).get())
//                .channel(channelRepository.findById(request.getCid()).get())
                .category(request.getCategory())
                .description(request.getDescription())
                .duration(request.getDuration())
                .largeThumbnailUrl("https://img.youtube.com/vi/"+ request.getVid() +"/sddefault.jpg")
                .smallThumbnailUrl("https://img.youtube.com/vi/"+ request.getVid() +"/mqdefault.jpg")
                .title(request.getTitle())
                .views(request.getViews())
                .build();

        // 4. 디비에 넣고 response로 만들고 졍상 리턴
        return response(videoRepository.save(video));
    }

    // 영상 읽기
    @Transactional(readOnly = true)
    public Header<VideoApiResponse> read(String vid) {
        // 1. 디비에서 꺼내오기
        Video video = videoRepository.findById(vid).get();

        // 2. Video를 response로 만들고 졍상 리턴
        return response(video);
    }

    // 영상 수정하기
    @Transactional
    public Header<VideoApiResponse> update(VideoApiRequest request) {
        // 1. 디비에서 꺼내오기
        Video video = videoRepository.findById(request.getVid()).get();
        video.setChannel(channelRepository.findByCid(request.getCid()).get());
//        video.setChannel(channelRepository.findById(request.getCid()).get());
        video.setCategory(request.getCategory());
        video.setDescription(request.getDescription());
        video.setDuration(request.getDuration());
        video.setTitle(request.getTitle());
        video.setViews(request.getViews());
        video.setLargeThumbnailUrl(request.getLargeThumbnailUrl());
        video.setSmallThumbnailUrl(request.getSmallThumbnailUrl());

        // 3. 디비에 넣고 response로 만들고 졍상 리턴
        return response(videoRepository.save(video));
    }

    @Transactional
    public Header<VideoApiResponse> updateViews(String vid) {
        // 1. 디비에서 꺼내오기
        Video video = videoRepository.findById(vid).get();
        // View 1추가
        video.setViews(video.getViews() + 1);
        // 3. 디비에 넣고 response로 만들고 졍상 리턴
        return response(videoRepository.save(video));
    }

    // 영상 삭제
    @Transactional
    public Header delete(String vid) {
        // 1. 디비에서 꺼내오기
        Video video = videoRepository.findById(vid).get();

        // 2. 삭제
        videoRepository.delete(video);

        // 3. OK 리턴
        return Header.OK();
    }

    // 영상 전체 읽기
    @Transactional(readOnly = true)
    public Header<List<VideoApiResponse>> readAllPaging(Pageable pageable) {
        // 1. 디비에서 꺼내오기
        Page<Video> videoList = videoRepository.findAllPaging(pageable);
        // 2. 리스폰으로 바꾸기
        List<VideoApiResponse> responseList = videoList.stream()
                .map(entity -> {
                    return response(entity).getData();

                }).collect(Collectors.toList());
        // 3. 페이지 네이션 생성
        Pagination pagination = Pagination.builder()
                .totalPages(videoList.getTotalPages())
                .totalElements(videoList.getTotalElements())
                .currentPage(videoList.getNumber())
                .currentElements(videoList.getNumberOfElements())
                .build();
        // 4. 정상 리턴
        return Header.OK(responseList, pagination);
    }

    // 채널 영상들 읽기
    @Transactional(readOnly = true)
    // 20210217 개발할 동안 캐시 제거
    // @Cacheable(value = "readChannelVideos", key = "#cid + '_' + #pageable", cacheManager = "cacheManager")
    public Header<List<VideoApiResponse>> readChannelVideosPaging(String cid, Pageable pageable) {
        // 1. 디비에서 꺼내오기
        Page<Video> kkVideoList = videoRepository.readChannelVideosPaging(cid, pageable);
        // 2. 리스폰으로 바꾸기
        List<VideoApiResponse> responseList = kkVideoList.stream()
                .map(entity -> {
                    return response(entity).getData();
                }).collect(Collectors.toList());
        // 3. 페이지 네이션 생성
        Pagination pagination = Pagination.builder()
                .totalPages(kkVideoList.getTotalPages())
                .totalElements(kkVideoList.getTotalElements())
                .currentPage(kkVideoList.getNumber())
                .currentElements(kkVideoList.getNumberOfElements())
                .build();
        // 4. 정상 리턴
        return Header.OK(responseList, pagination);
    }

    // vid + 연관 영상들 읽기
    @Transactional(readOnly = true)
    public Header<VideoRelatedApiResponse> readRelatedVideos(String vid) {
        // 0. 디비에서 영상 하나 들고오기
        Video video = videoRepository.findById(vid).get();

        // 1. 홍보영상 3개, 채널영상 5개, 전체영상 50개 랜덤으로 들고온다
        List<Video> allVideoList = videoRepository.readVideosRand();
        List<Video> channelVideoList = videoRepository.readChannelVideosRand(video.getChannel().getCid());
        List<Video> adVideoList = videoRepository.readAdVideoRand();

        // 2. 내 나름의 방식으로 섞기 채홍채홍채홍채채전전전~~~
        List<Video> resultList = Stream.concat(adVideoList.stream(), channelVideoList.stream()).collect(Collectors.toList());
        resultList.addAll(allVideoList);


        // 3. 리스폰으로 바꾸기
        VideoApiResponse rResponse = response(video).getData();
        List<VideoApiResponse> cResponseList = resultList.stream()
                .distinct() // 중복 제거
                .filter(entity -> !entity.getVid().equals(vid)) // 나 자신은 빼기
                .map(entity -> { // 리스폰으로 바꾸기
                    return response(entity).getData();
                }).collect(Collectors.toList());

        VideoRelatedApiResponse response = VideoRelatedApiResponse.builder()
                .video(rResponse)
                .relatedVideos(cResponseList)
                .build();
        // 4. 정상 리턴
        return Header.OK(response);
    }

    // 내가 구독한 채널들의 영상들 읽기
    @Transactional(readOnly = true)
    // 20210217 개발할 동안 캐시 제거
    // @Cacheable(value = "readMySubscribeVideosPaging", key = "#uid + '_' + #pageable", cacheManager = "cacheManager")
    public Header<List<VideoApiResponse>> readMySubscribeVideosPaging(Long uid, Pageable pageable) {
        // 1. 디비에서 꺼내오기
     //   List<KKVideo> kkVideoList = kkVideoRepository.readMySubscribeVideos(uid);
        Page<Video> kkVideoList = videoRepository.readMySubscribeVideosPaging(uid, pageable);
        // 2. 리스폰으로 바꾸기
        List<VideoApiResponse> responseList = kkVideoList.stream()
                .map(entity -> {
                    return response(entity).getData();

                }).collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(kkVideoList.getTotalPages())
                .totalElements(kkVideoList.getTotalElements())
                .currentPage(kkVideoList.getNumber())
                .currentElements(kkVideoList.getNumberOfElements())
                .build();
        // 4. 정상 리턴
        return Header.OK(responseList, pagination);
    }

    // 영상 검색하기
    @Transactional(readOnly = true)
    public Header<List<VideoApiResponse>> readSearchVideos(String searchText) {
        // 1. 디비에서 꺼내오기
        String searchPattern = "%" + searchText + "%";
        List<Video> kkVideoList = videoRepository.readSearchVideos(searchPattern);
        // 2. 리스폰으로 바꾸기
        List<VideoApiResponse> responseList = kkVideoList.stream()
                .map(entity -> {
                    return response(entity).getData();

                }).collect(Collectors.toList());
        // 3. 정상 리턴
        return Header.OK(responseList);
    }

    // 리스폰 만들기
    public Header<VideoApiResponse> response(Video video){
        // 1. 광고영상 -> 광고영상 리스폰
        VideoApiResponse response = VideoApiResponse.builder()
                .vid(video.getVid())
                .cid(video.getChannel().getCid())
                .category(video.getCategory())
                .description(video.getDescription())
                .duration(video.getDuration())
                .largeThumbnailUrl(video.getLargeThumbnailUrl())
                .smallThumbnailUrl(video.getSmallThumbnailUrl())
                .title(video.getTitle())
                .views(video.getViews())
                .createdAt(video.getCreatedAt())
                .updatedAt(video.getUpdatedAt())
                .channelProfileUrl(video.getChannel().getProfileUrl())
                .channelCName(video.getChannel().getCName())
                .build();

        // 2. 헤더 붙이기 & 리턴
        return Header.OK(response);
    }
}

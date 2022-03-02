package com.adiscope.kkpoint.channel;

import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.common.Pagination;
import com.adiscope.kkpoint.notice_event.NoticeEvent;
import com.adiscope.kkpoint.subscribe.SubscribeRepository;
import com.adiscope.kkpoint.video.Video;
import com.adiscope.kkpoint.video.VideoApiResponse;
import com.adiscope.kkpoint.video.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by 박영호(eeyatho@neowiz.com) on 2021-01-26.
 */
@RequiredArgsConstructor
@Service
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final VideoRepository videoRepository;
    private final SubscribeRepository subscribeRepository;

    // 채널 추가하기
    @Transactional
    public Header<ChannelApiResponse> create(ChannelApiRequest request) {
        // 1. 이미 있으면 업데이트 안하기
//        if (channelRepository.existsById(request.getCid()))
//            return Header.ERROR("cid Error : 이미 등록되어 있습니다.");

        // 2. request로 엔티티 만들기
        Channel channel = Channel.builder()
                .cName(request.getCName())
                .profileUrl(request.getProfileUrl())
                .description(request.getDescription())
                .descriptionAdmin(request.getDescriptionAdmin())
                .build();

        // 3. 디비에 넣고 리스폰으로 만들고 졍상 리턴
        return response(channelRepository.save(channel));
    }

    // 채널 읽기
    @Transactional(readOnly = true)
    public Header<ChannelApiResponse> read(String cid) {
        // 1. 디비에서 꺼내오기
//        Channel channel = channelRepository.findById(cid).get();
        Optional<Channel> optional = channelRepository.findByCid(cid);

        return optional.map(channel -> {
            return  response(channel);
        })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
        // 2. 엔티티를 response로 만들고 졍상 리턴
//        return response(channel);
    }

//    // 채널 수정하기
//    @Transactional
//    public Header<ChannelApiResponse> update(ChannelApiRequest request) {
//        // 1. 디비에서 꺼내오기
//        Channel channel = channelRepository.findById(request.getCid()).get();
//
//        // 2. 수정하기
//        channel.setCName(request.getCName());
//        channel.setProfileUrl(request.getProfileUrl());
//        channel.setDescription(request.getDescription());
//        channel.setDescriptionAdmin(request.getDescriptionAdmin());
//
//        // 3. 디비에 넣고 리스폰으로 만들고 졍상 리턴
//        return response(channelRepository.save(channel));
//    }
//
//    // 채널 삭제
//    @Transactional
//    public Header delete(String cid) {
//        // 1. 디비에서 꺼내오기
//        Channel channel = channelRepository.findById(cid).get();
//
//        // 2. 삭제
//        channelRepository.delete(channel);
//
//        // 3. OK 리턴
//        return Header.OK();
//    }

//    // 채널 전체 읽기
//    @Transactional(readOnly = true)
//    public Header<List<ChannelApiResponse>> readAll() {
//        // 1. 디비에서 꺼내오기
//        List<Channel> channelList = channelRepository.findAll();
//        // 2. 리스폰으로 바꾸기
//        List<ChannelApiResponse> responseList = channelList.stream()
//                .map(entity -> {
//                    return response(entity).getData();
//
//                }).collect(Collectors.toList());
//        // 3. 정상 리턴
//        return Header.OK(responseList);
//    }

    // 내 구독 채널 리스트 읽기
    @Transactional(readOnly = true)
    public Header<List<ChannelApiResponse>> readMySubscribeChannels(Long uid) {
        // 1. 디비에서 꺼내오기
        List<Channel> channelList = channelRepository.findAll(uid);
        // 2. 리스폰으로 바꾸기
        List<ChannelApiResponse> responseList = channelList.stream()
                .map(entity -> {
                    return response(entity).getData();

                }).collect(Collectors.toList());

        // 3. 정상 리턴
        return Header.OK(responseList);
    }

    // 채널 검색하기
    @Transactional(readOnly = true)
    public Header<List<ChannelApiResponse>> readSearchChannels(String searchText) {
        // 1. 디비에서 꺼내오기
        String searchPattern = "%" + searchText + "%";
        List<Channel> channelList = channelRepository.readSearchChannels(searchPattern);
        // 2. 리스폰으로 바꾸기
        List<ChannelApiResponse> responseList = channelList.stream()
                .map(entity -> {
                    return response(entity).getData();

                }).collect(Collectors.toList());

        // 3. 정상 리턴
        return Header.OK(responseList);
    }

    // 엔티티로 리스폰 만들기
    public Header<ChannelApiResponse> response(Channel channel){
        // 1. 엔티티 -> 리스폰
        ChannelApiResponse response = ChannelApiResponse.builder()
                .cid(channel.getCid())
                .cName(channel.getCName())
                .profileUrl(channel.getProfileUrl())
                .description(channel.getDescription())
                .descriptionAdmin(channel.getDescriptionAdmin())
                .createdAt(channel.getCreatedAt())
                .updatedAt(channel.getUpdatedAt())
                .subscribeCnt(subscribeRepository.getSubscribeNum(channel.getCid()))
                .videoCnt(videoRepository.getVideoNum(channel.getCid()))
                .build();

        // 2. 헤더 붙이기 & 리턴
        return Header.OK(response);
    }
}

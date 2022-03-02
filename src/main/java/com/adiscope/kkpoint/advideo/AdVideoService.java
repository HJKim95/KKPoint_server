package com.adiscope.kkpoint.advideo;

import com.adiscope.kkpoint.video.VideoRepository;
import com.adiscope.kkpoint.common.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 박영호(eeyatho@neowiz.com) on 2021-01-27.
 */
@RequiredArgsConstructor
@Service
public class AdVideoService {
    private final AdVideoRepository adVideoRepository;
    private final VideoRepository videoRepository;

    // 홍보 영상 추가하기
    @Transactional
    public Header<AdVideoApiResponse> create(AdVideoApiRequest request) {
        // 1. 이미 있으면 업데이트 안하기
        if (adVideoRepository.existsById(request.getVid()))
            return Header.ERROR("vid Error : 이미 광고영상에 등록되어 있습니다.");

        // 2. vid 유효성 체크
        if (!videoRepository.existsById(request.getVid()))
            return Header.ERROR("vid Error : 등록되지 않은 영상입니다.");

        // 3. request로 AdVideo 만들
        AdVideo adVideo = AdVideo.builder()
                .vid(request.getVid())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .displayFlag(request.getDisplayFlag())
                .build();

        // 4. 디비에 넣고, response로 만들고 졍상 리턴
        return response(adVideoRepository.save(adVideo));
    }

    // 홍보 영상 읽기
    @Transactional(readOnly = true)
    public Header<AdVideoApiResponse> read(String vid) {
        // 1. 디비에서 꺼내오기
        AdVideo adVideo = adVideoRepository.findById(vid).get();

        // 2. AdVideo를 response로 만들고 졍상 리턴
        return response(adVideo);
    }

    // 홍보 영상 수정하기
    @Transactional
    public Header<AdVideoApiResponse> update(AdVideoApiRequest request) {
        // 1. 디비에서 꺼내오기
        AdVideo adVideo = adVideoRepository.findById(request.getVid()).get();

        adVideo.setStartDate(request.getStartDate());
        adVideo.setEndDate(request.getEndDate());
        adVideo.setDisplayFlag(request.getDisplayFlag());

        // 2. 업데이트하고, 리스폰으로 만들고 졍상 리턴
        return response(adVideoRepository.save(adVideo));
    }

    // 홍보 영상 삭제
    @Transactional
    public Header delete(String vid) {
        // 1. 디비에서 꺼내오기
        AdVideo adVideo = adVideoRepository.findById(vid).get();

        // 2. 삭제
        adVideoRepository.delete(adVideo);

        // 3. OK 리턴
        return Header.OK();
    }

    // 홍보 영상 전체 읽기
    @Transactional(readOnly = true)
    public Header<List<AdVideoApiResponse>> readAll() {
        // 1. 디비에서 꺼내오기
        List<AdVideo> adVideoList = adVideoRepository.findAll();
        // 2. 리스폰으로 바꾸기
        List<AdVideoApiResponse> responseList = adVideoList.stream()
                .map(entity -> {
                    return response(entity).getData();

                }).collect(Collectors.toList());
        // 3. 정상 리턴
        return Header.OK(responseList);
    }


    // 광고영상 정상 리스폰 만들기
    @Transactional
    public Header<AdVideoApiResponse> response(AdVideo adVideo){
        // 1. 광고영상 -> 광고영상 리스폰
        AdVideoApiResponse response = AdVideoApiResponse.builder()
                .vid(adVideo.getVid())
                .startDate(adVideo.getStartDate())
                .endDate(adVideo.getEndDate())
                .displayFlag(adVideo.getDisplayFlag())
                .createdAt(adVideo.getCreatedAt())
                .build();

        // 2. 헤더 붙이기 & 리턴
        return Header.OK(response);
    }
}

package com.adiscope.kkpoint.video;

import com.adiscope.kkpoint.common.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VideoItemService {
    private final VideoItemRepository videoItemRepository;

    @Transactional(readOnly = true)
    public Header<List<VideoItemDTO>> readAllVideoItems(String vid) {
        // 1. 디비에서 꺼내오기
        List<VideoItem> videoItemList = videoItemRepository.getAllVideoItem(vid);
        // 2. 리스폰으로 바꾸기
        List<VideoItemDTO> videoItemDTOList = videoItemList.stream()
                .map(videoItem -> {
                    return response(videoItem).getData();
                }).collect(Collectors.toList());

        return Header.OK(videoItemDTOList);
    }

    // 리스폰 만들기
    public Header<VideoItemDTO> response(VideoItem videoItem){
        // 1. 광고영상 -> 광고영상 리스폰
        VideoItemDTO response = VideoItemDTO.builder()
                .vid(videoItem.getVid())
                .vItemImageUrl(videoItem.getVItemImageUrl())
                .vItemLinkUrl(videoItem.getVItemLinkUrl())
                .vItemName(videoItem.getVItemName())
                .vItemPrice(videoItem.getVItemPrice())
                .build();

        // 2. 헤더 붙이기 & 리턴
        return Header.OK(response);
    }
}

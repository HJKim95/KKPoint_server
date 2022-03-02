package com.adiscope.kkpoint.search;

import com.adiscope.kkpoint.channel.ChannelApiResponse;
import com.adiscope.kkpoint.video.VideoApiResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchResponse {
    private List<VideoApiResponse> videoList;

    private List<ChannelApiResponse> channelList;

    private List<Boolean> subscribeList;
}

package com.adiscope.kkpoint.channel;

import com.adiscope.kkpoint.video.VideoApiResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChannelHomeResponse {
    private ChannelApiResponse channel;

    private Boolean isSubscribed;

    private List<VideoApiResponse> videoList;
}

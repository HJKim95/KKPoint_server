package com.adiscope.kkpoint.video;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VideoRelatedApiResponse {
    private VideoApiResponse video;

    private List<VideoApiResponse> relatedVideos;
}

package com.adiscope.kkpoint.video;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoApiRequest {
    private String vid;

    private String largeThumbnailUrl;

    private String smallThumbnailUrl;

    private String title;

    private Long views;

    private String category;

    private Long duration;

    private String cid;

    private String description;
}

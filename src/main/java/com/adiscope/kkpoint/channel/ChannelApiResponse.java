package com.adiscope.kkpoint.channel;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChannelApiResponse {
    private String cid;

    private String cName;

    private String profileUrl;

    private String description;

    private String descriptionAdmin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long subscribeCnt;

    private Long videoCnt;
}

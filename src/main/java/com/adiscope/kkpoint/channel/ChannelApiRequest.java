package com.adiscope.kkpoint.channel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelApiRequest {
    private String cid;

    private String cName;

    private String profileUrl;

    private String description;

    private String descriptionAdmin;
}

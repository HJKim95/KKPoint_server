package com.adiscope.kkpoint.subscribe;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SubscribeApiResponse {
    private SubscribePK uidAndCid;

    private LocalDateTime createdAt;
}

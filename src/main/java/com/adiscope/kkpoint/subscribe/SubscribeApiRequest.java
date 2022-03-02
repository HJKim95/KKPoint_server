package com.adiscope.kkpoint.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubscribeApiRequest {
    private SubscribePK uidAndCid;
}

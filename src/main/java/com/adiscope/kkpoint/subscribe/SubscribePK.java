package com.adiscope.kkpoint.subscribe;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Embeddable // 복합키 사용을 위한 것
public class SubscribePK implements Serializable {
    private Long uid;
    private String cid;
}
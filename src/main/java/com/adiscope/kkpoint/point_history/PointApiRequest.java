package com.adiscope.kkpoint.point_history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointApiRequest {

    private Integer amount;

    private String content;
}

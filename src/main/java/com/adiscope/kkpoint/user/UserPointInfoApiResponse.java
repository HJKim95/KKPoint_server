package com.adiscope.kkpoint.user;

import com.adiscope.kkpoint.point_history.PointApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPointInfoApiResponse {
    private Integer totalPoints;
    private List<PointApiResponse> pointApiResponseList;
}

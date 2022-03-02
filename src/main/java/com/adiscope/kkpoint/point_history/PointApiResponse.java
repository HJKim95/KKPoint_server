package com.adiscope.kkpoint.point_history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointApiResponse {

    private Integer amount;

    private String content;

    private LocalDateTime createdAt;

}

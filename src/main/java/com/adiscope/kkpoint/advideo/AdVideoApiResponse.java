package com.adiscope.kkpoint.advideo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdVideoApiResponse {
    private String vid;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean displayFlag;

    private LocalDateTime createdAt;
}

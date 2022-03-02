package com.adiscope.kkpoint.advideo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdVideoApiRequest {
    private String vid;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean displayFlag;
}

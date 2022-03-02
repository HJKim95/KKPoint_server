package com.adiscope.kkpoint.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceApiResponse {
    private Long idx;

    private Long uid;

    private LocalDate createdAt;
}
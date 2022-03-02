package com.adiscope.kkpoint.notice_event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeEventApiRequest {

    private Long idx;

    private String title;

    private String contentUrl;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean displayFlag;

    private String content;
}

package com.adiscope.kkpoint.notice_event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kk_notice_event")
@Builder
@Accessors(chain = true)
public class NoticeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String title;

    private String contentUrl;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean displayFlag;

    private String content;

}

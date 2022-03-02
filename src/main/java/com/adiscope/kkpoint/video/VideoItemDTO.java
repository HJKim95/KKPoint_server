package com.adiscope.kkpoint.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoItemDTO {

    private String vid;

    private String vItemImageUrl;

    private String vItemName;

    private Integer vItemPrice;

    private String vItemLinkUrl;

}
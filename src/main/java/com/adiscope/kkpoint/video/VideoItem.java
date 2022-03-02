package com.adiscope.kkpoint.video;

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
@Table(name = "kk_video_item")
@Builder
@Accessors(chain = true)
public class VideoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String vid;

    private String vItemImageUrl;

    private String vItemName;

    private Integer vItemPrice;

    private String vItemLinkUrl;

}

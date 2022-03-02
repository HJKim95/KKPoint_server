package com.adiscope.kkpoint.video;

import com.adiscope.kkpoint.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "kk_video")
public class Video {
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private String vid;
    @Column
    private String largeThumbnailUrl;
    @Column
    private String smallThumbnailUrl;
    @Column
    private String title;
    @Column
    private Long views;
    @Column
    private String category;
    @Column
    private Long duration;
//    @Column
//    private String cid;
    @Column
    private String description;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "cid")
    private Channel channel;
}
package com.adiscope.kkpoint.channel;

import com.adiscope.kkpoint.video.Video;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor // 매개변수없이 생성
@AllArgsConstructor
@Data
@Builder

@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "kk_channel")
public class Channel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String cid;
    @Column
    private String cName;
    @Column
    private String profileUrl;
    @Column
    private String description;
    @Column
    private String descriptionAdmin;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY)
    private List<Video> videos;
}
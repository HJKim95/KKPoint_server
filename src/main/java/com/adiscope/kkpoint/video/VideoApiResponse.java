package com.adiscope.kkpoint.video;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoApiResponse {
    private String vid;

    private String largeThumbnailUrl;

    private String smallThumbnailUrl;

    private String title;

    private Long views;

    private String category;

    private Long duration;

    private String cid;

    private String description;

    // 20210217 LocalDataTime은 @Cacheable로 Redis에 캐시할 경우, 직렬화, 역직렬화에 이슈가 있어서 별도의 처리가 필요함.
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    // 20210217 LocalDataTime은 @Cacheable로 Redis에 캐시할 경우, 직렬화, 역직렬화에 이슈가 있어서 별도의 처리가 필요함.
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

    private String channelProfileUrl;

    private String channelCName;
}

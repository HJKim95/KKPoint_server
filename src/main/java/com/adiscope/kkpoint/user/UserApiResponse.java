package com.adiscope.kkpoint.user;

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
public class UserApiResponse {

    private Long idx;

    private String socialType;

    private String name;

    private String email;

    private Boolean enableAlarm;

    private LocalDate enableAlarmDate;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}

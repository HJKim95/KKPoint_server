package com.adiscope.kkpoint.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiRequest {

    private String socialType;

    private String name;

    private String email;

    private Boolean enableAlarm;

    private LocalDate enableAlarmDate;

}

package com.adiscope.kkpoint.mgmt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegularTestResponse {

    private String regularTestMessage;

    private LocalDateTime regularTestStart;

    private LocalDateTime regularTestEnd;

}

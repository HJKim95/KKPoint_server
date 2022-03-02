package com.adiscope.kkpoint.mgmt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegularTestRequest {

    private String regularTestMessage;

    private LocalDateTime regularTestStart;

    private LocalDateTime regularTestEnd;

}
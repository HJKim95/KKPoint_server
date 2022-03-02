package com.adiscope.kkpoint.sign.NaverDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverResponse {
    private String id;
    private String email;
    private String name;
}
package com.adiscope.kkpoint.customer_service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerServiceResponse {

    private String content;

    private String userEmail;

    private String userName;

    private Long userId;
}
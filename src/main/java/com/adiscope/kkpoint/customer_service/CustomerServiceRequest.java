package com.adiscope.kkpoint.customer_service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerServiceRequest {
    private String content;

    private String userEmail;

    private String userName;
}

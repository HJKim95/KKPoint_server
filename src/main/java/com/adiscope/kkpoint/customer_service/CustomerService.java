package com.adiscope.kkpoint.customer_service;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kk_customer_service")
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
public class CustomerService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String content;

    private String userEmail;

    private String userName;

    private Long userId;

    @CreatedDate
    private LocalDateTime createdAt;

}

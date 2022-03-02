package com.adiscope.kkpoint.Adiscope;

import com.adiscope.kkpoint.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kk_adiscope")
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
public class Adiscope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String transactionId;

    private String signature;

    private String unitId;

    private String userId;

    private String adid;

    private String rewardUnit;

    private String network;

    private String rewardAmount;

    private String adtype;

    private String adname;

    @CreatedDate
    private LocalDateTime createdAt;

}
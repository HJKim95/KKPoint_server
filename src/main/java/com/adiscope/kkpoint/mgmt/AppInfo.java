package com.adiscope.kkpoint.mgmt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kk_app_info")
@Builder
@Accessors(chain = true)
public class AppInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String iosMinVersion;

    private String aosMinVersion;

    private String iosMarketVersion;

    private String aosMarketVersion;

    private String regularTestMessage;

    private LocalDateTime regularTestStartDate;

    private LocalDateTime regularTestEndDate;

}
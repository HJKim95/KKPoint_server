package com.adiscope.kkpoint.config.token.loginHistory;

import com.adiscope.kkpoint.config.token.BaseEntity;
import com.adiscope.kkpoint.config.token.enums.ActionCode;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Data
@Entity
@Builder
@Table(name = "kk_login_history")
public class LoginHistory extends BaseEntity {
    private static final long serialVersionUID = -6656679489188120930L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idx;

    @Column
    private Long userIdx;

    @Column
    private String deviceId;

    @Enumerated(EnumType.STRING)
    @Column(length = 6, nullable = false)
    private ActionCode actionCode;

}
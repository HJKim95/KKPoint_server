package com.adiscope.kkpoint.config.token.tokenStore;

import com.adiscope.kkpoint.config.token.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@Entity
@Table(name = "token_store")
public class TokenStore extends BaseEntity {

    private static final long serialVersionUID = -5725139907594942837L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private Long memberIdx;

    @Column
    private String refreshToken;


}
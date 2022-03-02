package com.adiscope.kkpoint.config.token.tokenStore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenStoreRepository extends JpaRepository<TokenStore, Long> {

    TokenStore findByRefreshToken(String refreshToken);

    @Modifying
    @Query("delete from TokenStore i where i.refreshToken = :refreshToken")
    void deleteByRefreshToken(@Param("refreshToken") String refreshToken);

    @Modifying
    @Query("delete from TokenStore i where i.memberIdx = :memberIdx")
    void deleteByMemberIdx(@Param("memberIdx") Long memberIdx);
}

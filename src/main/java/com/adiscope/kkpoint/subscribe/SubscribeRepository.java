package com.adiscope.kkpoint.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, SubscribePK> {
    // 구독자 수
    @Query("SELECT COUNT(s) FROM Subscribe s " +
            "WHERE s.uidAndCid.cid = :cid ")
    Long getSubscribeNum(String cid);
}

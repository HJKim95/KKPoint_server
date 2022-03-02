package com.adiscope.kkpoint.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    // 내가 구독한 채널 가져오기
    @Query("SELECT c FROM Channel c, Subscribe s " +
            "WHERE c.cid = s.uidAndCid.cid AND s.uidAndCid.uid = :uid ")
    List<Channel> findAll(Long uid);

    Optional<Channel> findByCid(String cid);

    // 채널 검색하기
    @Query("SELECT c FROM Channel c " +
            "WHERE c.cName LIKE :pattern ")
    List<Channel> readSearchChannels(String pattern);
}

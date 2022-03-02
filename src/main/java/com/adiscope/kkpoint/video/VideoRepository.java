package com.adiscope.kkpoint.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, String> {

    // MARK: - 연관영상 관련
    // 광고 영상들 받기. (랜덤 3개) for 연관영상
    @Query(nativeQuery = true, value =
            "SELECT * FROM kk_video v, kk_ad_video a " +
            "WHERE v.vid = a.vid " +
            "AND a.displayFlag = true " +
            "AND a.startDate <= now() AND a.endDate >= now() " +
            "ORDER BY rand() LIMIT 5 ")
    List<Video> readAdVideoRand();

    // 채널 내의 영상들 받기. (랜덤 5개) for 연관영상
    @Query(nativeQuery = true, value =
            "SELECT * FROM kk_video v " +
            "WHERE v.cid = :cid " +
            "ORDER BY rand() LIMIT 5 ")
    List<Video> readChannelVideosRand(String cid);

    // 아무 영상 받기. (랜덤 50개) for 연관영상
    @Query(nativeQuery = true, value =
            "SELECT * FROM kk_video " +
            "ORDER BY rand() LIMIT 50 ")
    List<Video> readVideosRand();

    // 홈화면용 전체 비디오 페이징
    @Query("SELECT k FROM Video k " +
            "ORDER BY k.createdAt DESC ")
    Page<Video> findAllPaging(Pageable pageable);

    // 채널 내의 영상들 받기.
//    @Query("SELECT k FROM Video k " +
//            "WHERE k.channel.cid = :cid ")
//    List<Video> readChannelVideos(String cid);

    // 채널 내의 영상들 받기.(페이징)
    @Query("SELECT k FROM Video k " +
            "WHERE k.channel.cid = :cid " +
            "ORDER BY k.createdAt DESC ")
    Page<Video> readChannelVideosPaging(String cid, Pageable pageable);

    // 내가 구독한 채널들의 영상들 받기.(페이징)
    @Query("SELECT k FROM Video k, Subscribe ks " +
            "WHERE ks.uidAndCid.uid = :uid " +
            "AND k.channel.cid = ks.uidAndCid.cid " +
            "ORDER BY k.createdAt DESC ")
//            "AND ks.uidAndCid.cid = c.cid" )
    Page<Video> readMySubscribeVideosPaging(Long uid, Pageable pageable);

    // 현재 광고로 등록된 영상들 List<Video> 로 받기. 플래그랑 시간 체크까지.
//    @Query("SELECT k FROM Video k " +
//            "LEFT JOIN AdVideo ka ON k.vid = ka.vid " +
//            "AND ka.displayFlag = true " +
//            "AND ka.startDate <= :now AND ka.endDate >= :now ")
//    List<Video> readAllAdVideo(LocalDate now);

    // 비디오 검색하기
    @Query("SELECT k FROM Video k " +
            "WHERE k.title LIKE :pattern ")
    List<Video> readSearchVideos(String pattern);

    // 채널의 영상 수
    @Query("SELECT COUNT(v) FROM Video v " +
            "WHERE v.channel.cid = :cid ")
    Long getVideoNum(String cid);
}

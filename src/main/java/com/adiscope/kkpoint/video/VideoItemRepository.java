package com.adiscope.kkpoint.video;

import com.adiscope.kkpoint.point_history.PointHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoItemRepository extends JpaRepository<VideoItem, String> {
    @Query("SELECT vi FROM VideoItem vi WHERE vi.vid = :vid ")
    List<VideoItem> getAllVideoItem(String vid);
}
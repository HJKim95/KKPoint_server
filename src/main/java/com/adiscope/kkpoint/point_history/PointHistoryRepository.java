package com.adiscope.kkpoint.point_history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    @Query("SELECT ph FROM PointHistory ph WHERE ph.user.idx = :id ")
    Page<PointHistory> pagingPointHistory(Long id, Pageable pageable);
}

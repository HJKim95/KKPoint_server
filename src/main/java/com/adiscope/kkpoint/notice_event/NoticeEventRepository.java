package com.adiscope.kkpoint.notice_event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeEventRepository extends JpaRepository<NoticeEvent, Long> {
}

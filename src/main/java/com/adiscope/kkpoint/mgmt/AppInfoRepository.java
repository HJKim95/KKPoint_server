package com.adiscope.kkpoint.mgmt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInfoRepository extends JpaRepository<AppInfo, Long> {
}

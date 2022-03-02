package com.adiscope.kkpoint.config.token.loginHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    LoginHistory findTop1ByUserIdxOrderByIdxDesc(Long memberIdx);
}

package com.adiscope.kkpoint.config.token.loginHistory;

import com.adiscope.kkpoint.config.token.enums.ActionCode;
import com.adiscope.kkpoint.common.custom_exception.InvalidDeviceException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class LoginHistoryService {
    private final LoginHistoryRepository loginHistoryRepository;

    @Transactional
    public void register(Long memberIdx, ActionCode actionCode, String deviceId) {
        LoginHistory loginHistory = loginHistoryRepository.findTop1ByUserIdxOrderByIdxDesc(memberIdx);

        if(loginHistory != null) {
            if(!StringUtils.equalsIgnoreCase(loginHistory.getDeviceId(), deviceId)) {
                //device 변경 일어남
                throw new InvalidDeviceException();
            }else {
//                if(actionCode == loginHistory.getActionCode()) {
//                    //동일 행동을 기본 토큰 만료 시간 사이에 반복하는 경우
//                    if(JwtTokenProvider.tokenValidMillisecond >
//                            LocalDateTime.now().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli() - loginHistory.getCreatedAt().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()) {
//                        throw new AlreadyActionException();
//                    }
//                }else{
                    loginHistoryRepository.save(LoginHistory.builder().userIdx(memberIdx).actionCode(actionCode).deviceId(deviceId).build());
//                }

            }
        } else {
            loginHistoryRepository.save(LoginHistory.builder().userIdx(memberIdx).actionCode(actionCode).deviceId(deviceId).build());
        }


    }
}

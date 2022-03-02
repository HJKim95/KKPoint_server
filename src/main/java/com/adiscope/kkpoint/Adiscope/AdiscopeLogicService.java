package com.adiscope.kkpoint.Adiscope;

import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.point_history.PointHistory;
import com.adiscope.kkpoint.point_history.PointHistoryRepository;
import com.adiscope.kkpoint.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdiscopeLogicService {

    private final AdiscopeRepository adiscopeRepository;

    private final UserRepository userRepository;

    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public Header<AdiscopeDTO> create(AdiscopeDTO request) {

        AdiscopeDTO adiscopeDTO = request;

        Adiscope adiscope = Adiscope.builder()
                .transactionId(adiscopeDTO.getTransactionId())
                .signature(adiscopeDTO.getSignature())
                .unitId(adiscopeDTO.getUnitId())
                .userId(adiscopeDTO.getUserId())
                .adid(adiscopeDTO.getAdid())
                .rewardUnit(adiscopeDTO.getRewardUnit())
                .network(adiscopeDTO.getNetwork())
                .rewardAmount(adiscopeDTO.getRewardAmount())
                .adtype(adiscopeDTO.getAdtype())
                .adname(adiscopeDTO.getAdname())
                .build();
        
        String content = "광고 리워드 지급";
        if (adiscopeDTO.getUnitId().contains("RV")) {
            content = "보상형 광고 리워드 지급";
        } else if (adiscopeDTO.getUnitId().contains("OFFERWALL")) {
            content = "오퍼월 광고 리워드 지급";
        }

        if (!adiscopeDTO.getUserId().equals("AnonymousUser")) {
            PointHistory pointHistory = PointHistory.builder()
                    .amount(Integer.parseInt(adiscopeDTO.getRewardAmount()))
                    .content(content)
                    .user(userRepository.getOne(Long.parseLong(adiscopeDTO.getUserId())))
                    .build();
            pointHistoryRepository.save(pointHistory);
        }

        adiscopeRepository.save(adiscope);

        return Header.OK();
    }
}

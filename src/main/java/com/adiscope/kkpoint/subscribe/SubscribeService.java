package com.adiscope.kkpoint.subscribe;

import com.adiscope.kkpoint.user.UserRepository;
import com.adiscope.kkpoint.channel.ChannelRepository;
import com.adiscope.kkpoint.common.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by 박영호(eeyatho@neowiz.com) on 2021-01-26.
 */
@RequiredArgsConstructor
@Service
public class SubscribeService {
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final SubscribeRepository subscribeRepository;

    // 구독 하기
    @Transactional
    public Header<SubscribeApiResponse> create(Long uid, String cid) {
        // 1. uid 유효성 체크
        if (!userRepository.existsById(uid))
            return Header.ERROR("uid Error : 존재하지 않는 uid 입니다.");

        // 2. cid 유효성 체크 // 추후 existsByCid 해서 수정
//        if (!channelRepository.existsById(cid))
//            return Header.ERROR("cid Error : 존재하지 않는 cid 입니다.");

        // 3. 엔티티 만듦
        SubscribePK subscribePK = SubscribePK.builder().uid(uid).cid(cid).build();
        Subscribe subscribe = Subscribe.builder()
                .uidAndCid(subscribePK)
                .build();

        // 4. 디비에 넣고 response로 만들고 졍상 리턴
        return response(subscribeRepository.save(subscribe));
    }

    // 구독 취소하기
    @Transactional
    public Header delete(Long uid, String cid) {
        // 1. 디비에서 꺼내오기
        SubscribePK subscribePK = SubscribePK.builder().uid(uid).cid(cid).build();
        Subscribe subscribe = subscribeRepository.findById(subscribePK).get();
        // 2. 지우기
        subscribeRepository.delete(subscribe);
        // 3. OK 리턴
        return Header.OK();
    }

    // 구독한 상태인지 확인하기
    @Transactional(readOnly = true)
    public Header<Boolean> getSubscribeStatus(Long uid, String cid) {
        // 0. PK 만들고
        SubscribePK subscribePK = SubscribePK.builder()
                .uid(uid)
                .cid(cid)
                .build();

        // 1. 디비에서 꺼내오기
        Optional<Subscribe> subscribe = subscribeRepository.findById(subscribePK);

        // 2. 체크 후 분기
        if (subscribe.isPresent()) {
            return Header.OK(true);
        } else {
            return Header.OK(false);
        }
    }
    // 엔티티로 리스폰 만들기
    public Header<SubscribeApiResponse> response(Subscribe subscribe){
        // 1. 엔티티 -> 리스폰
        SubscribeApiResponse response = SubscribeApiResponse.builder()
                .uidAndCid(subscribe.getUidAndCid())
                .createdAt(subscribe.getCreatedAt())
                .build();

        // 2. 헤더 붙이기 & 리턴
        return Header.OK(response);
    }
}

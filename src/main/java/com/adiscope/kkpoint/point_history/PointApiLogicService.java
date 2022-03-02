package com.adiscope.kkpoint.point_history;

import com.adiscope.kkpoint.user.UserRepository;
import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointApiLogicService {

    private final UserRepository userRepository;

    private final PointHistoryRepository pointHistoryRepository;

    // 1. request data
    // 2. user 생성
    // 3. 생성된 데이터 -> UserApiResponse return
    @Transactional
    public Header<PointApiResponse> create(PointApiRequest request, Long id) {


        PointApiRequest pointApiRequest = request;

        PointHistory pointHistory = PointHistory.builder()
                .amount(pointApiRequest.getAmount())
                .content(pointApiRequest.getContent())
                .user(userRepository.getOne(id))
                .build();

        // 3. 생성된 데이터 -> userApiResponse return
        return response(pointHistoryRepository.save(pointHistory));
    }

    public Header<PointApiResponse> response(PointHistory pointHistory){
        // user -> userApiResponse

        PointApiResponse pointApiResponse = PointApiResponse.builder()
                .amount(pointHistory.getAmount())
                .content(pointHistory.getContent())
                .createdAt(pointHistory.getCreatedAt())
                .build();

        // Header + data return
        return Header.OK(pointApiResponse);
    }
}

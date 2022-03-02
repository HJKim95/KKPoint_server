package com.adiscope.kkpoint.notice_event;

import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.common.Pagination;
import com.adiscope.kkpoint.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeEventApiLogicService {

    private final NoticeEventRepository noticeEventRepository;

    @Transactional
    public Header<NoticeEventApiResponse> create(NoticeEventApiRequest request) {

        // 1. request data
        NoticeEventApiRequest noticeEventApiRequest = request;
        // 2. User 생성
        NoticeEvent noticeEvent = NoticeEvent.builder()
                .title(noticeEventApiRequest.getTitle())
                .contentUrl(noticeEventApiRequest.getContentUrl())
                .startDate(noticeEventApiRequest.getStartDate())
                .endDate(noticeEventApiRequest.getEndDate())
                .displayFlag(noticeEventApiRequest.getDisplayFlag())
                .content(noticeEventApiRequest.getContent())
                .build();

        return response(noticeEventRepository.save(noticeEvent));
    }

    @Transactional(readOnly = true)
    public Header<List<NoticeEventApiResponse>> search(Pageable pageable) {
        Page<NoticeEvent> kkNoticeEvents = noticeEventRepository.findAll(pageable);
        List<NoticeEventApiResponse> noticeEventApiResponseList = kkNoticeEvents.stream()
                .map(noticeEvent -> response(noticeEvent).getData())
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(kkNoticeEvents.getTotalPages())
                .totalElements(kkNoticeEvents.getTotalElements())
                .currentPage(kkNoticeEvents.getNumber())
                .currentElements(kkNoticeEvents.getNumberOfElements())
                .build();

        return Header.OK(noticeEventApiResponseList, pagination);
    }

    @Transactional
    public Header<NoticeEventApiResponse> update(NoticeEventApiRequest request) {
        // 1. data
        NoticeEventApiRequest noticeEventApiRequest = request;

        // 2. id -> user 데이터 를 찾고
        Optional<NoticeEvent> optional = noticeEventRepository.findById(noticeEventApiRequest.getIdx());

        return optional.map(noticeEvent -> {
            // 3. data -> update
            // idx도 같이 요청해주고 idx는 위에서 체크한다.
            noticeEvent.setTitle(noticeEventApiRequest.getTitle())
                    .setContentUrl(noticeEventApiRequest.getContentUrl())
                    .setStartDate(noticeEventApiRequest.getStartDate())
                    .setEndDate(noticeEventApiRequest.getEndDate())
                    .setDisplayFlag(noticeEventApiRequest.getDisplayFlag())
                    .setContent(noticeEventApiRequest.getContent())
            ;
            return noticeEvent;

        })
                .map(noticeEvent -> noticeEventRepository.save(noticeEvent))
                .map(noticeEvent -> response(noticeEvent))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }


    @Transactional
    public Header delete(Long id) {
        // 1. id -> repository -> user
        Optional<NoticeEvent> optional = noticeEventRepository.findById(id);

        // 2. repository -> delete
        return optional.map(noticeEvent ->{
            noticeEventRepository.delete(noticeEvent);
            return Header.OK();
        })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<NoticeEventApiResponse> response(NoticeEvent noticeEvent){
        // user -> userApiResponse

        NoticeEventApiResponse noticeEventApiResponse = NoticeEventApiResponse.builder()
                .idx(noticeEvent.getIdx())
                .title(noticeEvent.getTitle())
                .contentUrl(noticeEvent.getContentUrl())
                .startDate(noticeEvent.getStartDate())
                .endDate(noticeEvent.getEndDate())
                .displayFlag(noticeEvent.getDisplayFlag())
                .content(noticeEvent.getContent())
                .build();

        // Header + data return
        return Header.OK(noticeEventApiResponse);
    }
}

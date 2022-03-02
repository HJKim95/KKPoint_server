package com.adiscope.kkpoint.notice_event;

import com.adiscope.kkpoint.common.CRUDController;
import com.adiscope.kkpoint.common.Header;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"Notice/Event"})
@RestController
@RequestMapping("/kkpoint/notice")
public class NoticeEventController {
    @Autowired
    protected NoticeEventApiLogicService noticeEventApiLogicService;

    @ApiOperation(value = "공지사항 읽기", notes = "공지사항을 가져온다 paging = 20")
    @GetMapping("")
    public Header<List<NoticeEventApiResponse>> search(@PageableDefault(sort = "startDate", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        log.info("{}",pageable);
        return noticeEventApiLogicService.search(pageable);
    }
}
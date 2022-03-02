package com.adiscope.kkpoint.mgmt;
import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.config.security.ManageBaseController;
import com.adiscope.kkpoint.notice_event.NoticeEventApiLogicService;
import com.adiscope.kkpoint.notice_event.NoticeEventApiRequest;
import com.adiscope.kkpoint.notice_event.NoticeEventApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Api(tags = {"Management"})
@RestController
@RequestMapping("/kkpoint/noticeEventManagement")
public class NoticeEventMgmtController extends ManageBaseController {


    @Autowired
    NoticeEventApiLogicService noticeEventApiLogicService;

    @ApiOperation(value = "공지사항 작성", notes = "공지사항을 작성합니다.")
    @PostMapping("")
    public Header<NoticeEventApiResponse> create(@RequestBody NoticeEventApiRequest request, HttpServletRequest servletRequest) throws IOException {
        accessCheck(servletRequest);
        log.info("{}",request);
        return noticeEventApiLogicService.create(request);
    }

    @ApiOperation(value = "공지사항 수정", notes = "공지사항을 수정합니다.")
    @PutMapping("")
    public Header<NoticeEventApiResponse> update(@RequestBody NoticeEventApiRequest request, HttpServletRequest servletRequest) throws IOException {
        log.info("{}",request);
        return noticeEventApiLogicService.update(request);
    }

    @ApiOperation(value = "공지사항 삭제", notes = "공지사항을 삭제합니다.")
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id, HttpServletRequest servletRequest) throws IOException {
        log.info("delete : {}",id);
        return noticeEventApiLogicService.delete(id);
    }
}

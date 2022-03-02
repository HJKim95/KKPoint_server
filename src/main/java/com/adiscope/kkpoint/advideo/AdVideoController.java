package com.adiscope.kkpoint.advideo;

import com.adiscope.kkpoint.common.Header;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
//@Api(tags = {"01. ADVideo"})
@RestController
@RequestMapping(value = "/kkpoint")
public class AdVideoController {
    @Autowired
    protected AdVideoService adVideoService;

//    @PostMapping("/adVideo")
//    public Header<AdVideoApiResponse> create(@RequestBody AdVideoApiRequest request) {
//        log.info("{}",request);
//        return adVideoService.create(request);
//    }
//
//    @GetMapping("/adVideo/{vid}")
//    public Header<AdVideoApiResponse> read(@PathVariable(name = "vid") String vid) {
//        log.info("read id : {}",vid);
//        return adVideoService.read(vid);
//    }

    // 광고 영상들 받아오기
//    @GetMapping("/adVideos")
//    public Header<List<AdVideoApiResponse>> readAll() {
//        return adVideoService.readAll();
//    }

//    @PutMapping("/adVideo/{vid}")
//    public Header<AdVideoApiResponse> update(@RequestBody AdVideoApiRequest request) {
//        return adVideoService.update(request);
//    }
//
//    @DeleteMapping("/adVideo/{vid}")
//    public Header delete(@PathVariable String vid) {
//        log.info("delete : {}",vid);
//        return adVideoService.delete(vid);
//    }
}

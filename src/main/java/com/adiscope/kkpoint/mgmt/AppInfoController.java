package com.adiscope.kkpoint.mgmt;

import com.adiscope.kkpoint.common.Header;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = {"AppInfo"})
@RestController
@RequestMapping("/kkpoint/appInfo")
public class AppInfoController {

    @Autowired
    protected AppInfoService appInfoService;

    @ApiOperation(value = "킥킥포인트 os별 버전 설정", notes = "OS별 버전정보와 정기점검 내용을 확인합니다.")
    @GetMapping("/readInfo")
    public Header<AppInfoResponse> checkVersion(@RequestParam(value = "os") String  os) throws IllegalAccessException {
        log.info("checking Verison: {}",os);
        return appInfoService.readVersion(os);
    }

    @ApiOperation(value = "킥킥포인트 최소 지원버전 설정", notes = "강제업데이트를 위한 킥킥포인트 최소지원버전을 설정합니다.")
    @PutMapping("/minVersion")
    public Header<AppInfoResponse> updateMinVersion(@RequestParam(value = "os") String  os,
                                                       @RequestParam(value = "minVersion") String  version) throws IllegalAccessException {
        log.info("checking MinVerison: {}",os);
        return appInfoService.updateMinVersion(os, version);
    }

    @ApiOperation(value = "킥킥포인트 마켓버전 설정", notes = "킥킥포인트 마켓버전을 설정합니다.")
    @PutMapping("/marketVersion")
    public Header<AppInfoResponse> updateMarketVersion(@RequestParam(value = "os") String  os,
                                                    @RequestParam(value = "marketVersion") String  version) throws IllegalAccessException {
        log.info("checking MarketVerison: {}",os);
        return appInfoService.updateMarketVersion(os, version);
    }

    @ApiOperation(value = "정기점검 설정", notes = "정기점검이 필요할 때 설정합니다.")
    @PutMapping("/regularTest")
    public Header<RegularTestResponse> updateRegularTest(@RequestParam(value = "pwd") String  pwd,
                                                        @RequestBody RegularTestRequest request) throws IllegalAccessException {
        log.info("checking Verison: {}",pwd);
        return appInfoService.updateRegularTest(pwd, request);
    }

}
package com.adiscope.kkpoint.Adiscope;

import com.adiscope.kkpoint.attendance.AttendanceApiLogicService;
import com.adiscope.kkpoint.attendance.AttendanceApiResponse;
import com.adiscope.kkpoint.common.Header;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = {"Adiscope"})
@RequestMapping("/kkpoint/adiscope")
public class AdiscopeController {

    @Autowired
    AdiscopeLogicService adiscopeLogicService;

    @ApiOperation(value = "에디스콥 보상 콜백 저장", notes = "에디스콥을 통해 RV, 오퍼월을 시청하여 보상 콜백이 들어온 경우 DB 저장을 위해 이용한다.")
    @GetMapping("")
    public Header<AdiscopeDTO> getAdiscopeInfo(@RequestParam(value = "transactionId") String  transactionId,
                                                         @RequestParam(value = "signature") String  signature,
                                                         @RequestParam(value = "unitId") String  unitId,
                                                         @RequestParam(value = "userId") String  userId,
                                                         @RequestParam(value = "adid") String  adid,
                                                         @RequestParam(value = "rewardUnit") String  rewardUnit,
                                                         @RequestParam(value = "network") String  network,
                                                         @RequestParam(value = "rewardAmount") String  rewardAmount,
                                                         @RequestParam(value = "adtype") String  adtype,
                                                         @RequestParam(value = "adname") String  adname) {

        AdiscopeDTO adiscopeDTO = AdiscopeDTO.builder()
                .transactionId(transactionId)
                .signature(signature)
                .unitId(unitId)
                .userId(userId)
                .adid(adid)
                .rewardUnit(rewardUnit)
                .network(network)
                .rewardAmount(rewardAmount)
                .adtype(adtype)
                .adname(adname)
                .build();

        return adiscopeLogicService.create(adiscopeDTO);
    }

}

package com.adiscope.kkpoint.customer_service;

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
@Api(tags = {"CustomerService"})
@RestController
@RequestMapping("/kkpoint/CustomerService")
public class CustomerServiceController {

    protected String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userId;
    }

    @Autowired
    protected CustomerServiceApiLogicService customerServiceApiLogicService;

    @ApiOperation(value = "문의하기", notes = "이용자가 문의하기를 통해 문의하였을 때 사용한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("")
    public Header<CustomerServiceResponse> createCustomerService(@RequestBody CustomerServiceRequest customerServiceRequest) {
        String userId = getUserId();
        Long id = Long.parseLong(userId);
        log.info("{}",id);
        return customerServiceApiLogicService.createCS(id, customerServiceRequest);
    }
}
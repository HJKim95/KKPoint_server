package com.adiscope.kkpoint.customer_service;

import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.user.User;
import com.adiscope.kkpoint.user.UserRepository;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceApiLogicService {

    private final UserRepository userRepository;

    private final CustomerServiceRepository customerServiceRepository;


    @Transactional
    public Header<CustomerServiceResponse> createCS(Long id, CustomerServiceRequest customerServiceRequest) {
        // user
        User user = userRepository.getOne(id);
        System.out.println(customerServiceRequest);

        CustomerService customerService = CustomerService.builder()
                .userEmail(customerServiceRequest.getUserEmail())
                .userName(customerServiceRequest.getUserName())
                .content(customerServiceRequest.getContent())
                .userId(id)
                .build();
        // 왜 new line이 안먹을까..ㅜ
        String messageContext = String.format("이름: %s" + System.lineSeparator()
                        + "이메일: %s" + System.lineSeparator() + System.lineSeparator()
                        + "내용: %s",
                customerServiceRequest.getUserName(), customerServiceRequest.getUserEmail(), customerServiceRequest.getContent());

        Sender sender = new Sender();
        SenderDto dto = new SenderDto("kk_help@neowiz.com", Lists.newArrayList("kk_help@neowiz.com"), "킥킥포인트 문의사항 접수", messageContext);

        sender.send(dto);

        return response(customerServiceRepository.save(customerService));
    }

    public Header<CustomerServiceResponse> response(CustomerService customerService){
        // user -> userApiResponse

        CustomerServiceResponse customerServiceResponse= CustomerServiceResponse.builder()
                .content(customerService.getContent())
                .userEmail(customerService.getUserEmail())
                .userName(customerService.getUserName())
                .userId(customerService.getUserId())
                .build();

        // Header + data return
        return Header.OK(customerServiceResponse);
    }


}

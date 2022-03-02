package com.adiscope.kkpoint.config.token;

import com.adiscope.kkpoint.user.UserApiLogicService;
import com.adiscope.kkpoint.user.UserApiResponse;
import com.adiscope.kkpoint.config.token.enums.Role;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 최경근(keun0912@neowiz.com) on 2020-03-26.
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsService  implements UserDetailsService {

    private final UserApiLogicService userApiLogicService;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        Long id = Long.parseLong(userId);
        UserApiResponse userApiResponse = userApiLogicService.findByUserId(id);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));

        return new User(String.valueOf(userApiResponse.getIdx()), StringUtils.EMPTY, authorities);
    }
}

package com.adiscope.kkpoint.config.token.tokenStore;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class TokenStoreService {
    private final TokenStoreRepository tokenStoreRepository;

    @Transactional
    public void register(TokenStore tokenStore){
        tokenStoreRepository.save(tokenStore);
    }

    public TokenStore findByRefreshToken(String refreshToken) {
        return tokenStoreRepository.findByRefreshToken(refreshToken);
    }

    @Transactional
    public void deleteByRefreshToken(String refreshToken) {
        tokenStoreRepository.deleteByRefreshToken(refreshToken);
    }

    @Transactional
    public void deleteByMemberIdx(Long memberIdx) {
        tokenStoreRepository.deleteByMemberIdx(memberIdx);
    }

}

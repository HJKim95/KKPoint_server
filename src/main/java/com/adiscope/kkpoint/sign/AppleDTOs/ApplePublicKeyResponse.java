package com.adiscope.kkpoint.sign.AppleDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.adiscope.utils.JsonUtil;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplePublicKeyResponse {
    // 애플은 3개의 공개키를 줌
    private List<MaterialsOfApplePublicKey> keys;

    // 3개의 공개키 중 사용할 키 찾기
    public Optional<MaterialsOfApplePublicKey> getMatchedKeyBy(String kid, String alg) {
        return this.keys.stream()
                .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
                .findFirst();
    }

    static public ApplePublicKeyResponse getApplePublicKeys() {
        // URI 준비
        URI url = URI.create("https://appleid.apple.com/auth/keys");

        // Http Request 발싸
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);

        // Json 파싱
        JsonUtil jsonUtil = new JsonUtil();
        ApplePublicKeyResponse result = jsonUtil.convert(response.getBody().toString(), ApplePublicKeyResponse.class);
        return result;
    }
}
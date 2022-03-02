package com.adiscope.kkpoint.sign;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class GoogleDTO {
//    private String iss;
//    private String azp;
//    private String aud;
//    private String sub;
    private String email;
//    private String email_verified;
//    private String at_hash;
//    private String nonce;
    private String name;
//    private String picture;
//    private String given_name;
//    private String family_name;
//    private String locale;
//    private String iat;
//    private String exp;
//    private String alg;
//    private String kid;
//    private String typ;
}


package com.adiscope.kkpoint.sign.AppleDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialsOfApplePublicKey {
    private String kty;
    private String kid;
    private String use;
    private String alg;
    private String n;
    private String e;
}
package com.adiscope.kkpoint.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by 최경근(keun0912@neowiz.com) on 2020-03-08.
 */
class ClipServiceTest {

    @Test
    void md5Process() {

        // http://trotvod.mbcmpp.co.kr/trotvod/_definst_/mp4:mbcplus_mbcptrot/test/ToS_480p.mp4/playlist.m3u8?e=1586072078&h=15ba157d9dac3dc1a9cb2a79b13bb0ab
        String secret = "mbcptrot$";
        String url = StringUtils.remove("http://trotvod.mbcmpp.co.kr/trotvod/_definst_/mp4:mbcplus_mbcptrot/test/ToS_480p.mp4/playlist.m3u8", "/playlist.m3u8");
        String time = "1586072078";

        String high = secret + url  + "?e=" + time;

        String highMd5Hex = DigestUtils.md5Hex(high);

        assertEquals(highMd5Hex, "15ba157d9dac3dc1a9cb2a79b13bb0ab");

    }

    @Test
    void md5Process2() {

        // http://trotvod.mbcmpp.co.kr/trotvod/_definst_/mp4:mbcplus_mbcptrot/test/ToS_480p.mp4/playlist.m3u8?e=1586072078&h=15ba157d9dac3dc1a9cb2a79b13bb0ab
        String secret = "115al3s";
        String url = "http://localhost.com/o1/s/1M.zip?down.fun=get_method";
        String time = "1433030400";

        String high = secret + url  + "&e=" + time;

        String highMd5Hex = DigestUtils.md5Hex(high);

        assertEquals(highMd5Hex, "390c7cc6dc4f3a706b9fe7cb1e2660a2");

    }
}
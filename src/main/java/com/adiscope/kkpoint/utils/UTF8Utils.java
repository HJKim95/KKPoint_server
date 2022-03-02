package com.adiscope.kkpoint.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by 최경근(keun0912@neowiz.com) on 2020-04-07.
 */
public class UTF8Utils {
    public static byte[] toUTF8Bytes(final String s) {
        try {
            return s.getBytes("UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String truncateWhenUTF8(final String s, final int maxBytes) {
        int b = 0;
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);

            // ranges from http://en.wikipedia.org/wiki/UTF-8
            int skip = 0;
            int more;
            if (c <= 0x007f) {
                more = 1;
            } else if (c <= 0x07FF) {
                more = 2;
            } else if (c <= 0xd7ff) {
                more = 3;
            } else if (c <= 0xDFFF) {
                // surrogate area, consume next char as well
                more = 4;
                skip = 1;
            } else {
                more = 3;
            }

            if (b + more > maxBytes) {
                return s.substring(0, i);
            }
            b += more;
            i += skip;
        }
        return s;
    }
}

package com.adiscope.kkpoint.common.custom_exception;

public class NoCouponAvailable extends RuntimeException {
    public NoCouponAvailable(String msg, Throwable t) {
        super(msg, t);
    }

    public NoCouponAvailable(String msg) {
        super(msg);
    }

    public NoCouponAvailable() {
        super();
    }
}
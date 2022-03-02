package com.adiscope.kkpoint.common.custom_exception;

public class CouponConflictException extends RuntimeException {
    public CouponConflictException(String msg, Throwable t) {
        super(msg, t);
    }

    public CouponConflictException(String msg) {
        super(msg);
    }

    public CouponConflictException() {
        super();
    }
}
package com.adiscope.kkpoint.common.custom_exception;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException(String msg, Throwable t) {
        super(msg, t);
    }

    public RefreshTokenExpiredException(String msg) {
        super(msg);
    }

    public RefreshTokenExpiredException() {
        super();
    }
}

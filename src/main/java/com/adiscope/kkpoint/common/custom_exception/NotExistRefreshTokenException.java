package com.adiscope.kkpoint.common.custom_exception;

public class NotExistRefreshTokenException extends RuntimeException {
    public NotExistRefreshTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotExistRefreshTokenException(String msg) {
        super(msg);
    }

    public NotExistRefreshTokenException() {
        super();
    }
}

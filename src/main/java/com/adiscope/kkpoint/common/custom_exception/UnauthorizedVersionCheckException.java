package com.adiscope.kkpoint.common.custom_exception;

public class UnauthorizedVersionCheckException extends RuntimeException {
    public UnauthorizedVersionCheckException(String msg, Throwable t) {
        super(msg, t);
    }

    public UnauthorizedVersionCheckException(String msg) {
        super(msg);
    }

    public UnauthorizedVersionCheckException() {
        super();
    }
}
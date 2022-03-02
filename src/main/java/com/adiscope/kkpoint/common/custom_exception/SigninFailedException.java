package com.adiscope.kkpoint.common.custom_exception;

public class SigninFailedException extends RuntimeException {
    public SigninFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public SigninFailedException(String msg) {
        super(msg);
    }

    public SigninFailedException() {
        super();
    }
}

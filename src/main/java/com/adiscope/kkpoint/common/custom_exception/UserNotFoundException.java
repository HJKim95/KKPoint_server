package com.adiscope.kkpoint.common.custom_exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException() {
        super();
    }
}

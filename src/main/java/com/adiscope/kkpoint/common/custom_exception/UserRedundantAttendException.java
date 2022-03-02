package com.adiscope.kkpoint.common.custom_exception;

public class UserRedundantAttendException extends RuntimeException {
    public UserRedundantAttendException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserRedundantAttendException(String msg) {
        super(msg);
    }

    public UserRedundantAttendException() {
        super();
    }
}

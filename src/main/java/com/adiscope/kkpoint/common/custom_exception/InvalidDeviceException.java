package com.adiscope.kkpoint.common.custom_exception;

public class InvalidDeviceException extends RuntimeException {
    public InvalidDeviceException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidDeviceException(String msg) {
        super(msg);
    }

    public InvalidDeviceException() {
        super();
    }
}

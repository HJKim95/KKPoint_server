package com.adiscope.kkpoint.config.security;

public class InvalidIpException extends RuntimeException {
    public InvalidIpException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidIpException(String msg) {
        super(msg);
    }

    public InvalidIpException() {
        super();
    }
}

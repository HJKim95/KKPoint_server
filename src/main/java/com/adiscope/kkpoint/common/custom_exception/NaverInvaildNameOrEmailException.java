package com.adiscope.kkpoint.common.custom_exception;

public class NaverInvaildNameOrEmailException extends RuntimeException {
    public NaverInvaildNameOrEmailException(String msg, Throwable t) {
        super(msg, t);
    }

    public NaverInvaildNameOrEmailException(String msg) {
        super(msg);
    }

    public NaverInvaildNameOrEmailException() {
        super();
    }
}

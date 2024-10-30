package com.xm.starter.base.exception;

public class BaseException extends RuntimeException{
    private String code;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BaseException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public BaseException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

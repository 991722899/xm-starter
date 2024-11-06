package com.xm.starter.base.exception;

public class ServiceException extends BaseException{
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, String code) {
        super(message, code);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
